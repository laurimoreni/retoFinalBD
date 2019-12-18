package grupo3.retoFinalBD;

public class Alojamiento {

	private String signatura;
	private String documentname;
	private String turismdescription;
	private String lodgingtype;
	private String address;
	private String phone;
	private String tourismemail;
	private String web;
	private String marks;
	private String municipality;
	private Provincia provincia;
	private float latwgs84;
	private float lonwgs84;
	private String postalcode;
	private int capacity;
	private int restaurant;
	private int store;
	private int autocaravana;
	
	public Alojamiento(String signatura, String documentname, String turismdescription, String lodgingtype, String address, String phone, String tourismemail, String web, String marks, String municipality, Provincia provincia, float latwgs84, float lonwgs84, String postalcode, int capacity, int restaurant, int store, int autocaravana) {
		this.signatura = signatura;
		this.documentname = documentname;
		this.turismdescription = turismdescription;
		this.lodgingtype = lodgingtype;
		this.address = address;
		this.phone = phone;
		this.tourismemail = tourismemail;
		this.web = web;
		this.marks = marks;
		this.municipality = municipality;
		this.provincia = provincia;
		this.latwgs84 = latwgs84;
		this.lonwgs84 = lonwgs84;
		this.postalcode = postalcode;
		this.capacity = capacity;
		this.restaurant = restaurant;
		this.store = store;
		this.autocaravana = autocaravana;
	}
	
	public Alojamiento() {
		
	}
	
	public void setSignatura(String signatura) {
		this.signatura = signatura;
	}

	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}

	public void setTurismdescription(String turismdescription) {
		this.turismdescription = turismdescription;
	}

	public void setLodgingtype(String lodgingtype) {
		this.lodgingtype = lodgingtype;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setTourismemail(String tourismemail) {
		this.tourismemail = tourismemail;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public void setLatwgs84(float latwgs84) {
		this.latwgs84 = latwgs84;
	}

	public void setLonwgs84(float lonwgs84) {
		this.lonwgs84 = lonwgs84;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setRestaurant(int restaurant) {
		this.restaurant = restaurant;
	}

	public void setStore(int store) {
		this.store = store;
	}

	public void setAutocaravana(int autocaravana) {
		this.autocaravana = autocaravana;
	}

	public String getSignatura() {
		return signatura;
	}

	public String getDocumentname() {
		return documentname;
	}

	public String getTurismdescription() {
		return turismdescription;
	}

	public String getLodgingtype() {
		return lodgingtype;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getTourismemail() {
		return tourismemail;
	}

	public String getWeb() {
		return web;
	}

	public String getMarks() {
		return marks;
	}

	public String getMunicipality() {
		return municipality;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public float getLatwgs84() {
		return latwgs84;
	}

	public float getLonwgs84() {
		return lonwgs84;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getRestaurant() {
		return restaurant;
	}

	public int getStore() {
		return store;
	}

	public int getAutocaravana() {
		return autocaravana;
	}
}