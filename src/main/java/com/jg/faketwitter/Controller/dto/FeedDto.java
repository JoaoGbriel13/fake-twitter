package com.jg.faketwitter.Controller.dto;

import java.util.List;

public record FeedDto(List<FeedItemDto> feedItens,
                      int page,
                      int pageSize,
                      int totalPages,
                      long totalElements) {
}
