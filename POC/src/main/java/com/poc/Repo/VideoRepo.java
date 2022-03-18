package com.poc.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.poc.Model.UploadVideo;

@Repository
public interface VideoRepo extends JpaRepository<UploadVideo, Long> {
	
//    Video findByName(String name);
//
//    boolean existsByName(String name);
//
//    @Query(nativeQuery = true, value="SELECT name FROM video")
//    List<String> getAllEntryNames();
}