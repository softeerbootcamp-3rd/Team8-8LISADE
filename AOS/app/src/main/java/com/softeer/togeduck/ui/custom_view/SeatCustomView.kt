package com.softeer.togeduck.ui.custom_view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.softeer.togeduck.R
import com.softeer.togeduck.utils.ItemClickWithSeat
import com.softeer.togeduck.utils.fromDpToPx

class SeatCustomView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    companion object {
        private const val SEAT_SIZE = 50
        private const val SEAT_MARGIN_SIZE = 10
    }

    private var mode: SeatViewMode
    private var seatsCntPerRow: Int
    private var backSeatsCnt: Int
    private var totalRows: Int
    private var mySeatNum: Int
    private var soldOutSeatNums: List<Int>


    private var seatDiffCntInBackAndOtherRow: Int
    private var seatColWithAisleLeft: Int
    private var aisleSize: Int
    private var viewWidthSize: Int
    private var viewHeightSize: Int

    private var seatItems: MutableList<TextView>
    private var selectedSeat: TextView?
    var selectedSeatNum: Int

    var itemClick: ItemClickWithSeat? = null

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.SeatCustomView, 0, 0).run {
            try {
                mode = SeatViewMode.values()[getInt(R.styleable.SeatCustomView_mode, 0)]
                seatsCntPerRow = getString(R.styleable.SeatCustomView_seatsCntPerRow)?.toInt() ?: 0
                backSeatsCnt = getString(R.styleable.SeatCustomView_backSeatsCnt)?.toInt() ?: 0
                totalRows = getString(R.styleable.SeatCustomView_totalRows)?.toInt() ?: 0
                mySeatNum = getString(R.styleable.SeatCustomView_mySeatNum)?.toInt() ?: 0
                soldOutSeatNums =
                    stringToList(getString(R.styleable.SeatCustomView_soldOutSeatNums) ?: "")
            } finally {
                recycle()
            }
        }
        selectedSeatNum = -1
        selectedSeat = null
        seatItems = mutableListOf()

        seatDiffCntInBackAndOtherRow = backSeatsCnt - seatsCntPerRow
        seatColWithAisleLeft = calcSeatColWithAisleLeft()
        aisleSize = calcAisleSize()
        viewWidthSize =
            ((SEAT_SIZE + SEAT_MARGIN_SIZE) * seatsCntPerRow - SEAT_MARGIN_SIZE + calcAisleSize()).fromDpToPx()
        viewHeightSize = ((SEAT_SIZE + SEAT_MARGIN_SIZE) * totalRows + SEAT_SIZE).fromDpToPx()

        initSeatView()
    }

    fun setSeatsCntPerRow(value: String) {
        this.seatsCntPerRow = value.toInt()
        checkAllValueAssignedWithBindingAdapter()
    }

    fun setBackSeatsCnt(value: String) {
        this.backSeatsCnt = value.toInt()
        checkAllValueAssignedWithBindingAdapter()
    }

    fun setTotalRows(value: String) {
        this.totalRows = value.toInt()
        checkAllValueAssignedWithBindingAdapter()
    }

    fun setMySeatNum(value: String) { // READ_ONLY 모드에서만 호출
        this.mySeatNum = value.toInt()
        checkAllValueAssignedWithBindingAdapter()
    }

    fun setSoldOutSeatNums(value: String) { // SELECTABLE 모드에서만 호출
        this.soldOutSeatNums = stringToList(value)
        checkAllValueAssignedWithBindingAdapter()
    }

    private fun checkAllValueAssignedWithBindingAdapter() {
        if (seatsCntPerRow == 0 || backSeatsCnt == 0 || totalRows == 0) {
            return
        }
        if (mode == SeatViewMode.READ_ONLY && mySeatNum == 0) {
            return
        }
        seatDiffCntInBackAndOtherRow = backSeatsCnt - seatsCntPerRow
        seatColWithAisleLeft = calcSeatColWithAisleLeft()
        aisleSize = calcAisleSize()
        viewWidthSize =
            ((SEAT_SIZE + SEAT_MARGIN_SIZE) * seatsCntPerRow - SEAT_MARGIN_SIZE + calcAisleSize()).fromDpToPx()
        viewHeightSize =
            ((SEAT_SIZE + SEAT_MARGIN_SIZE) * totalRows + SEAT_SIZE).fromDpToPx()

        initSeatView()
    }

    private fun initSeatView() {
        makeAllSeatTextView()
        loadSeatsToView()
    }

    private fun stringToList(input: String): List<Int> {
        val regex = Regex("\\d+")
        val matches = regex.findAll(input)
        return matches.map { it.value.toInt() }.toList()
    }

    private fun calcSeatColWithAisleLeft() = when (seatsCntPerRow) {
        2 -> 1
        3, 4 -> 2
        else -> -1
    }

    private fun calcAisleSize(): Int {
        return if (seatDiffCntInBackAndOtherRow == 0) {
            SEAT_SIZE
        } else {
            seatDiffCntInBackAndOtherRow * (SEAT_SIZE + SEAT_MARGIN_SIZE)
        }
    }

    private fun makeAllSeatTextView() {
        for (row in 0..<totalRows) {
            for (col in 0..<seatsCntPerRow) {
                val seatNumber = row * seatsCntPerRow + col + 1
                val item = if (col < seatColWithAisleLeft) {
                    createSeat(col, row, 0, seatNumber)
                } else {
                    createSeat(col, row, aisleSize, seatNumber)
                }
                seatItems.add(item)
            }
        }
        for (col in 0..<backSeatsCnt) { // 마지막 줄 좌석 추가
            val seatNumber = totalRows * seatsCntPerRow + col + 1
            val item = if (seatDiffCntInBackAndOtherRow == 0 && col >= seatColWithAisleLeft) {
                createSeat(col, totalRows, aisleSize, seatNumber)
            } else {
                createSeat(col, totalRows, 0, seatNumber)
            }
            seatItems.add(item)
        }
    }

    private fun loadSeatsToView() {
        var seatNum = 1
        for (seatTextView in seatItems) {
            addSeatToView(seatTextView, seatNum++)
        }
        if (mode == SeatViewMode.SELECTABLE) {
            checkSoldOutSeat()
        } else {
            checkMySeat()
        }
    }

    private fun createSeat(col: Int, row: Int, addedLeftMargin: Int, seatNumber: Int): TextView {
        val item = TextView(context)
        val leftPos = col * (SEAT_SIZE + SEAT_MARGIN_SIZE) + addedLeftMargin
        val topPos = row * (SEAT_SIZE + SEAT_MARGIN_SIZE)

        val lp = LayoutParams(viewWidthSize, viewHeightSize)
        lp.run {
            lp.width = SEAT_SIZE.fromDpToPx()
            lp.height = SEAT_SIZE.fromDpToPx()
            lp.topMargin = topPos.fromDpToPx()
            lp.leftMargin = leftPos.fromDpToPx()
        }

        if (mode == SeatViewMode.SELECTABLE) {
            item.setOnClickListener {
                val clickedSeatNum = item.text.toString().toInt()
                if (selectedSeatNum == clickedSeatNum) {
                    deselectSeat(item, seatNumber)
                    itemClick?.onClick(false)
                } else {
                    selectSeat(item, seatNumber)
                    itemClick?.onClick(true)
                }
            }
        }

        return item.apply {
            layoutParams = lp
            gravity = Gravity.CENTER
        }
    }

    private fun addSeatToView(item: TextView, seatNumber: Int) {
        setSeatItemProperty(item, seatNumber)
        addView(item)
    }

    private fun checkSoldOutSeat() {
        for (i in soldOutSeatNums) {
            setSoldOutSeatItemProperty(seatItems[i - 1])
        }
    }

    private fun checkMySeat() {
        setSelectedSeatItemProperty(seatItems[mySeatNum - 1])
    }

    private fun setSeatItemProperty(item: TextView, seatNumber: Int) {
        item.run {
            background = ContextCompat.getDrawable(context, R.drawable.seat_item)
            text = seatNumber.toString()
            setTextColor(Color.BLACK)
            isEnabled = true
        }
    }


    private fun setSelectedSeatItemProperty(item: TextView) {
        item.run {
            background = ContextCompat.getDrawable(context, R.drawable.selected_seat_item)
            setTextColor(Color.WHITE)
        }
    }

    private fun setSoldOutSeatItemProperty(item: TextView) {
        item.run {
            background = ContextCompat.getDrawable(context, R.drawable.sold_out_seat_item)
            text = ""
            isEnabled = false
        }
    }

    private fun deselectSeat(item: TextView, seatNumber: Int) {
        selectedSeatNum = -1
        selectedSeat = null

        setSeatItemProperty(item, seatNumber)
    }

    private fun selectSeat(item: TextView, seatNumber: Int) {
        if (selectedSeat != null) {
            setSeatItemProperty(selectedSeat!!, selectedSeat!!.text.toString().toInt())
        }

        selectedSeatNum = seatNumber
        selectedSeat = item

        setSelectedSeatItemProperty(item)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(viewWidthSize, viewHeightSize)
    }

}

enum class SeatViewMode {
    SELECTABLE, READ_ONLY
}