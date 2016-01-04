package hk.org.jccpa.dementia.classpackage;

public class fmyinfo {
	public String id;
	public String name;
	public String phone;
	public String email;
	public String relationship;
	public String remarks;
	public String filepath;

	public fmyinfo(
			String id,
			String name,
			String phone,
			String email,
			String relationship,
			String remarks,
			String filepath){
		this.id=id;
		this.name=name;
		this.phone=phone;
		this.email=email;
		this.relationship=relationship;
		this.remarks=remarks;
		this.filepath=filepath;

	}
	public fmyinfo(
			String name,
			String phone,
			String email,
			String relationship,
			String remarks
			){
		this.name=name;
		this.phone=phone;
		this.email=email;
		this.relationship=relationship;
		this.remarks=remarks;
	}

}
