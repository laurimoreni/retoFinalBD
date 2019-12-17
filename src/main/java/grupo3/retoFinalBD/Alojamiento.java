package grupo3.retoFinalBD;

public class Alojamiento {

	private String signatura;
	private String documentname;
	private String turismdescription;
	private String lodgingtype;
	private String address;
	private int phone;
	private String turismemail;
	private String web;
	private String marks;
	private String municipality;
	private Provincia provincia;
	private float latwgs84;
	private float lonwgs84;
	private int postalcode;
	private int capacity;
	private int restaurant;
	private int store;
	private int autocaravana;
	
	public Alojamiento(String signatura, String documentname, String turismdescription, String lodgingtype, String address, int phone, String turismemail, String web, String marks, String municipality, Provincia provincia, float latwgs84, float lonwgs84, int postalcode, int capacity, int restaurant, int store, int autocaravana) {
		this.signatura = signatura;
		this.documentname = documentname;
		this.turismdescription = turismdescription;
		this.lodgingtype = lodgingtype;
		this.address = address;
		this.phone = phone;
		this.turismemail = turismemail;
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

	public int getPhone() {
		return phone;
	}

	public String getTurismemail() {
		return turismemail;
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

	public int getPostalcode() {
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