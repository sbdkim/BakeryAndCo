package customer.dao;

public class RegionVO {

	private int regionCode;
	private String regionName;
	public RegionVO() {
		super();
	}
	public RegionVO(int regionCode, String regionName) {
		super();
		this.regionCode = regionCode;
		this.regionName = regionName;
	}
	public int getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(int regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	@Override
	public String toString() {
		return "RegionVO [regionCode=" + regionCode + ", regionName=" + regionName + "]";
	}

	
	
}
