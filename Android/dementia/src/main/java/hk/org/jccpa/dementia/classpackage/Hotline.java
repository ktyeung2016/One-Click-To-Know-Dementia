package hk.org.jccpa.dementia.classpackage;

public class Hotline {
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    private String name;
    private String phone;

	public Hotline(
            String name,
            String phone){
		this.name=name;
		this.phone=phone;
	}
}
