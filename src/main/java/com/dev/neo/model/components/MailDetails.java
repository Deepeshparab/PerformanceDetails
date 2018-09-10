package com.dev.neo.model.components;

public class MailDetails {
	private String computername;
	private String diskdrive;
	private Long totalCapacity;
	private Double freePartitionSpace;
	
	
	public MailDetails(String computername, Long totalCapacity, Double freePartitionSpace) {
		this.computername=computername;
		this.totalCapacity=totalCapacity;
		this.freePartitionSpace=freePartitionSpace;
	}
	public String getComputername() {
		return computername;
	}
	public void setComputername(String computername) {
		this.computername = computername;
	}
	public String getDiskdrive() {
		return diskdrive;
	}
	public void setDiskdrive(String diskdrive) {
		this.diskdrive = diskdrive;
	}
	public Long getTotalCapacity() {
		return totalCapacity;
	}
	public void setTotalCapacity(Long totalCapacity) {
		this.totalCapacity = totalCapacity;
	}
	public Double getFreePartitionSpace() {
		return freePartitionSpace;
	}
	public void setFreePartitionSpace(Double freePartitionSpace) {
		this.freePartitionSpace = freePartitionSpace;
	}
}
