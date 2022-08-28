package com.fhdufhdu.mim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fhdufhdu.mim.dto.searchhistory.SearchHistoryListElem;
import com.fhdufhdu.mim.entity.SearchHistory;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    @Query("select new com.fhdufhdu.mim.dto.searchhistory.SearchHistoryListElem(sh.searchText, sh.searchTime) from SearchHistory st join st.member m where m.id = :memberId")
    Page<SearchHistoryListElem> findByMemberId(String memberId, PageRequest pageRequest);

}
