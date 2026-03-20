package com.example.sample.utils;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import com.example.sample.util.PagenationUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PagenationTests {
    @Test
    void getTotalPagesTest() {
        int totalCount = 10;
        int pageSize = 3;
        int totalPages = PagenationUtil.getTotalPages(totalCount, pageSize);
        log.info("Total pages: {}", totalPages); // Expected output: Total pages: 4
    }

    @Test
    void pagenationListTest() {
        var list = IntStream.rangeClosed(1, 500).boxed().toList(); // Creates a list of integers from 1 to 500
        int pageSize = 90;
        int pageNumber = 4;
        var paginatedList = PagenationUtil.pagenationList(list, pageSize, pageNumber);
        log.info("Paginated list: {}", paginatedList); // Expected output: Paginated list: [item3, item4]
    }
}
