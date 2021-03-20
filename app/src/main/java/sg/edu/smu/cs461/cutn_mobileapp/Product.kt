package sg.edu.smu.cs461.cutn_mobileapp

class Product{
    // PRODUCTID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY VARCHAR(256), PRODUCTNAME VARCHAR(256), QUANTITY VARCHAR(256), DESCRIPTION VARCHAR(256), PRICE REAL, COUNTRY VARCHAR(256))"
    var productid: Int =  0
    var productname : String = ""
    var quantity : String = ""
    var description : String = ""
    var price : Float = 0.0f
    var country : String = ""
    var category: String = ""

    constructor(category:String,productname:String,quantity:String,description:String,price:Float,country:String){
        this.category = category
        this.productname = productname
        this.quantity = quantity
        this.description = description
        this.price = price
        this.country = country
    }

    constructor(productname: String,price: Float,quantity: String,description: String){
        this.productname = productname
        this.price = price
        this.quantity = quantity
        this.description = description
    }

    constructor(){

    }
}