package hk.org.jccpa.dementia.object;

public class soc {
	public String id;
	public String title_zh;
	public String title_gb;

	public String organization_zh;
	public String organization_gb;
	public String service_name1_zh;
	public String service_name1_gb;
	public String service_name2_zh;
	public String service_name2_gb;
	public String district_zh;
	public String district_gb;
	public String first;
	public String second;
	public String third;
	public String four;
	public String family;
	public String helper;
	public String remark_zh;
	public String remark_gb;
	public String phone;
	public String website;
	public String temp;
	public soc(
			String id,
			String title_zh,
			String title_gb,
			String organization_zh,
			String organization_gb,
			String service_name1_zh,
			String service_name1_gb,
			String service_name2_zh,
			String service_name2_gb,
			String district_zh,
			String district_gb,
			String first,
			String second,
			String third,
			String four,
			String family,
			String helper,
			String remark_zh,
			String remark_gb,
			String phone,
			String website,
			String temp
			){
		this.id=id;
		this.title_zh=title_zh;
		this.title_gb=title_gb;
		this.organization_zh=organization_zh;
		this.organization_gb=organization_gb;
		this.service_name1_zh=service_name1_zh;
		this.service_name1_gb=service_name1_gb;
		this.service_name2_zh=service_name2_zh;
		this.service_name2_gb=service_name2_gb;
		this.district_zh=district_zh;
		this.district_gb=district_gb;
		this.first=first;
		this.second=second;
		this.third=third;
		this.family=family;
		this.helper=helper;
		this.remark_zh=remark_zh;
		this.remark_gb=remark_gb;
		this.phone=phone;
		this.website=website;
		this.temp=temp;
		this.four=four;
	}
}
