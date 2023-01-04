package kkorionkk.test.bankcardtask

data class Info(
    var number: Number? = null,
    var scheme: String? = null,
    var type: String? = null,
    var brand: String? = null,
    var prepaid: Boolean = false,
    var country: Country? = null,
    var bank: Bank? = null,
)

data class Number(
    var length: Int = 0,
    var luhn: Boolean = false
)

data class Country(
    var numeric: String? = null,
    var alpha2: String? = null,
    var name: String? = null,
    var emoji: String? = null,
    var currency: String? = null,
    var latitude: Int = 0,
    var longitudeL: Int = 0
)

data class Bank(
    var name: String? = null,
    var url: String? = null,
    var phone: String? = null,
    var city: String? = null
)

