package com.plant.web.api.bookmark.adapter.out.persistence;

import com.plant.web.api.bookmark.domain.Bookmark;
import com.plant.web.api.member.domain.Member;
import com.plant.web.api.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface BookmarkJpaRepository extends JpaRepository<Bookmark, Serializable> {

    @Query(value = "SELECT bookmark_yn FROM tbl_bookmark WHERE place_id = :placeId and member_id = :memberId" , nativeQuery = true)
    String getBookmarkByBookmarkYN(@Param("placeId") String placeId, @Param("memberId") String memberId);

    Bookmark findByMemberAndPlace(Member member, Place place);
}
