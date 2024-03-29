package com.lisade.togeduck.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lisade.togeduck.cache.service.FestivalClickCountCacheService;
import com.lisade.togeduck.dto.response.FestivalDetailResponse;
import com.lisade.togeduck.dto.response.FestivalResponse;
import com.lisade.togeduck.entity.Category;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import com.lisade.togeduck.mapper.FestivalMapper;
import com.lisade.togeduck.repository.FestivalRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@ExtendWith(MockitoExtension.class)
class FestivalServiceImplTest {

    @Mock
    private FestivalRepository festivalRepository;

    @Mock
    private FestivalClickCountCacheService festivalClickCountCacheService;

    @Mock
    private FestivalMapper festivalMapper;

    @Mock
    ViewService viewService;
    @InjectMocks
    private FestivalServiceImpl festivalService;

    @Test
    @DisplayName("사용자로부터 Pageable, 카테고리, Status, 필터 종류를 입력받고 페이징된 FestivalDto를 반환한다.")
    void getList() {

        //given
        Festival mockFestival = getMockFestival(1L);
        Slice<Festival> mockFestivalSlice = new SliceImpl<>(
            Collections.singletonList(mockFestival));

        FestivalResponse mockFestivalResponse = FestivalResponse.builder().id(1L)
            .title("Fake Festival")
            .location("Fake location")
            .thumbnailPath("first").build();

        when(festivalMapper.toFestivalResponseSlice(any())).thenReturn(
            new SliceImpl<>(Collections.singletonList(mockFestivalResponse)));

        when(festivalRepository.findSliceByCategoryAndFestivalStatus(any(), any(),
            any())).thenReturn(
            mockFestivalSlice);

        //when
        Slice<FestivalResponse> result = festivalService.getList(PageRequest.of(0, 10),
            3L, FestivalStatus.RECRUITMENT, "testFilter");

        //then
        verify(festivalRepository, times(1)).findSliceByCategoryAndFestivalStatus(
            any(PageRequest.class),
            eq(3L), eq(FestivalStatus.RECRUITMENT));
        verify(festivalMapper, times(1)).toFestivalResponseSlice(mockFestivalSlice);
        assertThat(result.getContent().size()).isEqualTo(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Fake Festival");
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(result.getContent().get(0).getLocation()).isEqualTo("Fake location");
        assertThat(result.getContent().get(0).getThumbnailPath()).contains("first");
    }

    private Festival getMockFestival(Long festivalId) {
        return Festival.builder()
            .id(festivalId)
            .title("Fake Festival")
            .category(Category.builder().id(3L).build())
            .content("This is a fake festival")
            .location("Fake location")
            .xPos(0.0)
            .yPos(0.0)
            .startedAt(LocalDateTime.now())
            .festivalStatus(FestivalStatus.RECRUITMENT)
            .build();
    }

    @Test
    @DisplayName("사용자로부터 특정 행사의 id를 입력받고 해당하는 행사를 반환한다.")
    void get() {
        //given
        Long festivalId = 1L;
        Festival mockFestival = getMockFestival(festivalId);
        FestivalDetailResponse mockFestivalDetailResponse = FestivalDetailResponse
            .builder()
            .id(1L)
            .title("Fake FestivalDetailResponse").build();
        when(festivalRepository.findById(festivalId)).thenReturn(Optional.of(mockFestival));
        when(festivalMapper.toFestivalDetailResponse(mockFestival)).thenReturn(
            mockFestivalDetailResponse);
        User mockUser = mock(User.class);
        //when
        festivalService.getDetail(mockUser, festivalId);

        //then
        verify(festivalRepository, times(1)).findById(festivalId);
        verify(festivalClickCountCacheService, times(1)).increase(mockUser.getUserId(), festivalId);
        verify(festivalMapper, times(1)).toFestivalDetailResponse(mockFestival);
    }
}
