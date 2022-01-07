package oltest.appmt.cryptocurrency

class CoinModel{
    var id:String?=null
    var name:String?=null
    var symbol:String?=null
    var current_price:String?=null
    var high_24h:String?=null
    var low_24h:String?=null
    var price_change_24h:String?=null

    constructor(){}
    constructor(id: String, name: String, symbol: String, current_price: String, high_24h: String, low_24h: String, price_change_24h: String) {
        this.id = id
        this.name = name
        this.symbol = symbol
        this.current_price = current_price
        this.high_24h = high_24h
        this.low_24h = low_24h
        this.price_change_24h = price_change_24h
    }


}