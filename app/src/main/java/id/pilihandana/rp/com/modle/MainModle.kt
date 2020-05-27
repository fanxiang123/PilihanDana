package id.pilihandana.rp.com.modle

class MainModle {
    //产品id
    var id = 0

    //appid 目前无用
    var appid: String? = null

    //产品名称
    var productName: String? = null

    //利率
    var interestRate: String? = null

    //利率单位：天days, 月month
    var interestRateUnit: String? = null

    //放款时间（单位天）
    var loanDay: String? = null

    //放款最小金额
    var priceMin: String? = null

    //放款最大金额
    var priceMax: String? = null

    //放款单位金额
    var priceUnit: String? = null
    var icon: String? = null

    //综合评分
    var totalScore: String? = null

    //一句话说明
    var review: String? = null

    //产品包名
    var packageName: String? = null

    //api状态
    var api_status: String? = null

    //cap，导量上限
    var capLimit: String? = null

}