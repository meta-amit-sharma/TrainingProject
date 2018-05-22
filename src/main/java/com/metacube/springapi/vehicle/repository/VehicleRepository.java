package com.metacube.springapi.vehicle.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.metacube.springapi.vehicle.Vehicle;
import com.metacube.springapi.vehicle.enums.UseCaseName;
/**
 * Repository class
 * @author Amit Sharma
 *
 */
@Repository
public class VehicleRepository{
	
	@Autowired
	JdbcTemplate jdbcTemplate ;

	/**
	 * Function to search for a record in the database with the combination of 
	 * Vin, UseCaseName and SourceId
	 * @param vin
	 * @param useCaseName
	 * @param sourceId
	 * @return
	 *//*
	public List<Vehicle> findAllByVinAndUseCaseNameAndSourceId(String vin, UseCaseName useCaseName, String sourceId);*/
	
	public HttpStatus save(Vehicle vehicle) {
		String sql = "insert into vehicle(date, action, maintenance_gap_to_service, maintenance_gap_type, maintenance_service_due, message_id, source_id, tracking_id, use_case_name, vin,xml) values (?,?,?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] {vehicle.getDate(),vehicle.getAction(),vehicle.getMaintenanceGapToService(),vehicle.getMaintenanceGapType(),vehicle.getMaintenanceServiceDue(),vehicle.getMessageId(), vehicle.getSourceId(),vehicle.getTrackingId(), vehicle.getUseCaseName(),vehicle.getVin(), vehicle.getXml()}) != 0 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR ;
	}
	
	public List<Vehicle> findAllByVinAndUseCaseNameAndSourceId(String vin, String useCaseName, String sourceId){
		
		String sql = "SELECT * FROM VEHICLE WHERE VIN = ? AND  USE_CASE_NAME=? AND SOURCE_ID = ?";
		return jdbcTemplate.query(sql, new Object[] {vin, useCaseName, sourceId}, new VehicleMapper());
	}
	
	private final class VehicleMapper implements RowMapper<Vehicle>{

		@Override
		public Vehicle mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			return new Vehicle(resultSet.getDate("date"),
					            resultSet.getString("vin"),
					            resultSet.getString("message_id"),
					            resultSet.getString("action"), 
					            resultSet.getString("source_id"), 
					            resultSet.getString("tracking_id"),
					            resultSet.getString("use_case_name"),
					            resultSet.getInt("maintenance_gap_to_service"),
					            resultSet.getInt("maintenance_service_due"),
					            resultSet.getInt("maintenance_gap_type"),
					            resultSet.getClob("xml"));
		}
		
	}

}
