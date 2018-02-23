package com.boot.example.entity;

public class Warehouse {
	
	private String id;

	private String warehouseCode;

	private String warehouseName;

	private String masterId;

	private String masterName;

	private Integer warehouseType;

	private String warehouseTypeName;

	private String provinceId;

	private String provinceName;

	private String cityId;

	private String cityName;

	private String countyId;

	private String countyName;

	private String streetId;

	private String streetName;

	private String warehouseAddress;

	private Integer warehouseNegative;

	private String warehouseNegativeName;

	private String distributorId;
	
	private String storeId;
	
	private String storeCode;

	private Integer createFlag;// 创建者标识 ： 1.表示经销商创建 2.表示渠道管理创建 3.创建门店时创建
	
	private String createFlagName;
	
	private Integer isFrozen;
	
	private Integer delStatus;

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public Integer getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(Integer warehouseType) {
		this.warehouseType = warehouseType;
	}

	public String getWarehouseTypeName() {
		return warehouseTypeName;
	}

	public void setWarehouseTypeName(String warehouseTypeName) {
		this.warehouseTypeName = warehouseTypeName;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getStreetId() {
		return streetId;
	}

	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getWarehouseAddress() {
		return warehouseAddress;
	}

	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}

	public Integer getWarehouseNegative() {
		return warehouseNegative;
	}

	public void setWarehouseNegative(Integer warehouseNegative) {
		this.warehouseNegative = warehouseNegative;
	}

	public String getWarehouseNegativeName() {
		return warehouseNegativeName;
	}

	public void setWarehouseNegativeName(String warehouseNegativeName) {
		this.warehouseNegativeName = warehouseNegativeName;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Integer getCreateFlag() {
		return createFlag;
	}

	public void setCreateFlag(Integer createFlag) {
		this.createFlag = createFlag;
	}

	public String getCreateFlagName() {
		return createFlagName;
	}

	public void setCreateFlagName(String createFlagName) {
		this.createFlagName = createFlagName;
	}

	public Integer getIsFrozen() {
		return isFrozen;
	}

	public void setIsFrozen(Integer isFrozen) {
		this.isFrozen = isFrozen;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "Warehouse [id=" + id + ", warehouseCode=" + warehouseCode + ", warehouseName=" + warehouseName
				+ ", masterId=" + masterId + ", masterName=" + masterName + ", warehouseType=" + warehouseType
				+ ", warehouseTypeName=" + warehouseTypeName + ", provinceId=" + provinceId + ", provinceName="
				+ provinceName + ", cityId=" + cityId + ", cityName=" + cityName + ", countyId=" + countyId
				+ ", countyName=" + countyName + ", streetId=" + streetId + ", streetName=" + streetName
				+ ", warehouseAddress=" + warehouseAddress + ", warehouseNegative=" + warehouseNegative
				+ ", warehouseNegativeName=" + warehouseNegativeName + ", distributorId=" + distributorId + ", storeId="
				+ storeId + ", storeCode=" + storeCode + ", createFlag=" + createFlag + ", createFlagName="
				+ createFlagName + ", isFrozen=" + isFrozen + ", delStatus=" + delStatus + "]";
	}
	
}
