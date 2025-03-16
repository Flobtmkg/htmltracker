package flo.web.htmltracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import flo.web.htmltracker.Entities.Tracker;

@Repository
public interface TrackerRepository extends JpaRepository<Tracker, Integer> {
	
	@Query("SELECT trackers FROM Tracker trackers WHERE trackers.enable = true and trackers.trackingFrequency = :frequency")
	public List<Tracker> findAllEnableTrackerByTrackingFrequency(@Param("frequency")String frequency);
	
}
