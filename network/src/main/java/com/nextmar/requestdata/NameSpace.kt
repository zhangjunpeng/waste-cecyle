package com.nextmar.requestdata


object NameSpace {


    const val UesrName = "UserName"
    const val IsLogin = "isLogin"
    const val Token = "api_key"
    const val BundleExtraName = "bundle"
    const val SkuAcExtraName = "agglist"
    const val RemoveSkuAcExtraName = "removeagglist"

    const val SkuName = "sku_name"

    const val DetailToSkuAcExtraName = "skuinfo"


    val QualityDetailInfoItem = arrayOf("质检单号:", "采购单号:", "供应商:", "是否错发:", "应到货数量:")
    val QualityDetailInfoItemY =
        arrayOf(
            "check_order_number",
            "purchase_number",
            "supplier_name",
            "is_error",
            "should_arrival_num"
        )

    val QualityDetailSkuItem = arrayOf("SKU:", "到货数量:", "质检合格数量:", "质检不合格数量:", "留样数量:", "备注:")

    val OutStockDetailInfoItem = arrayOf("出库单号:", "出库分类:", "站点:", "仓库", "出库库区:", "出库库位:")
    val OutStockDetailInfoItemY = arrayOf(
        "out_stock_number",
        "type_id",
        "platform_id",
        "stock_name",
        "area_id",
        "warehouse_area_id"
    )

    val InStockAddInfoItem = arrayOf("入库单号:", "站点:", "入库分类:", "入库库区:", "入库库位:")
    val InStockAddInfoItemY =
        arrayOf("in_stock_number", "platform_id", "in_stock_type", "area_id", "warehouse_area_id")

    val InStockDetailInfoItem = arrayOf("入库单号:", "质检单号:", "入库分类:", "入库库区:", "入库库位:")
    val InStockDetailInfoItemY = arrayOf(
        "in_stock_number",
        "check_order_number:",
        "in_stock_type",
        "area_id",
        "warehouse_area_id"
    )

    val InventoryDetailInfoItem = arrayOf("盘点单号:", "仓库")
    val InventoryDetailInfoItemY = arrayOf("inventory_id", "stock_name")

    val AllocationDetailInfoItem = arrayOf("调拨单号:", "仓库")
    val AllocationDetailInfoItemY = arrayOf("allocation_id", "stock_name")

    val StockTransferDetailInfoItem = arrayOf("调拨单号:", "调出仓:", "调入仓:", "调拨负责人:")
    val StockTransferDetailInfoItemY =
        arrayOf("transfer_order_number", "out_stock", "in_stock", "response_person")

    val OrderStatus_Distribution = 2
    val OrderStatus_WithLens = 3
    val OrderStatus_Machining = 4
    val OrderStatus_Logo = 5
    val OrderStatus_Quality = 6


    val ScanActivityType = "ScanActivityType"
    val ScanActivityType_show = 1
    val ScanActivityType_Add = 2
    val ScanActivityType_Eddit = 3
    val ScanActivityType_Remove = 4


    val CreateTypeName = "CreateTypeName"
    val QualityCreate = 1
    val InStockCreate = 2
    val WaitExamineFromInStock = 3
    val WaitExamineFromQuality = 4

    val requestCodeName = "requestCode"

    val PageTypeName = "PageType"
    val PageTypeOut = "Out"
    val PageTypeIn = "In"

}
