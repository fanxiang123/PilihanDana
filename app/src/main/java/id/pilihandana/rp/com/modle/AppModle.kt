package id.pilihandana.rp.com.modle

class AppModle {
    //产品id
    private var pid: String? = null

    //产品名称
    private var product_name: String? = null

    //最小借款金额
    private var price_min: String? = null

    //最大借款金额
    private var price_max: String? = null

    //产品icon
    private var icon: String? = null

    //产品跳转链接
    private var jump_url: String? = null

    //通过率评分
    private var pass_rate_score: String? = null

    //放款速度评分
    private var speed_score: String? = null

    //催收评分
    private var dunning_score: String? = null

    //综合评分
    private var total_score: String? = null

    //产品包名
    private var package_name: String? = null

    //是否是apk产品，如果是apk产品，使用jump_url直接唤起手机内的浏览器打开下载
    private var apk_status: String? = null

    //借款额外描述
    private var loan_description: String? = null

    //借款利率参数集合
    private var interest_template: InterestTemplate? = null

    fun setPid(pid: String?) {
        this.pid = pid
    }

    fun getPid(): String? {
        return pid
    }

    fun setProduct_name(product_name: String?) {
        this.product_name = product_name
    }

    fun getProduct_name(): String? {
        return product_name
    }

    fun setPrice_min(price_min: String?) {
        this.price_min = price_min
    }

    fun getPrice_min(): String? {
        return price_min
    }

    fun setPrice_max(price_max: String?) {
        this.price_max = price_max
    }

    fun getPrice_max(): String? {
        return price_max
    }

    fun setIcon(icon: String?) {
        this.icon = icon
    }

    fun getIcon(): String? {
        return icon
    }

    fun setJump_url(jump_url: String?) {
        this.jump_url = jump_url
    }

    fun getJump_url(): String? {
        return jump_url
    }

    fun setPass_rate_score(pass_rate_score: String?) {
        this.pass_rate_score = pass_rate_score
    }

    fun getPass_rate_score(): String? {
        return pass_rate_score
    }

    fun setSpeed_score(speed_score: String?) {
        this.speed_score = speed_score
    }

    fun getSpeed_score(): String? {
        return speed_score
    }

    fun setDunning_score(dunning_score: String?) {
        this.dunning_score = dunning_score
    }

    fun getDunning_score(): String? {
        return dunning_score
    }

    fun setTotal_score(total_score: String?) {
        this.total_score = total_score
    }

    fun getTotal_score(): String? {
        return total_score
    }

    fun setPackage_name(package_name: String?) {
        this.package_name = package_name
    }

    fun getPackage_name(): String? {
        return package_name
    }

    fun setApk_status(apk_status: String?) {
        this.apk_status = apk_status
    }

    fun getApk_status(): String? {
        return apk_status
    }

    fun setLoan_description(loan_description: String?) {
        this.loan_description = loan_description
    }

    fun getLoan_description(): String? {
        return loan_description
    }

    fun setInterest_template(interest_template: InterestTemplate?) {
        this.interest_template = interest_template
    }

    fun getInterest_template(): InterestTemplate? {
        return interest_template
    }
}
