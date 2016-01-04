package hk.org.jccpa.dementia.object;

public class knowledge {
	public String title_zh;
	public String dbid;
	public String title_gb;
	public String html_content_zh;
	public String html_content_gb;
	public String redirect;
	public knowledge(

			String dbid,
			String redirect,
			String title_zh,
			String title_gb,
			String html_content_zh,
			String html_content_gb
			
			){		
		this.title_zh=title_zh;
		this.dbid=dbid;
		this.title_gb=title_gb;
		this.html_content_zh=html_content_zh;
		this.html_content_gb=html_content_gb;
		this.redirect=redirect;
	}
}
