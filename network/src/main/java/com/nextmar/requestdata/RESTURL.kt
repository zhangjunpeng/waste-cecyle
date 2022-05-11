package com.nextmar.requestdata


class RESTURL {
    companion object {
        val BaseURL_Test = "https://testtrace.yuanfangyun.com/app"
        val BaseUrl = BaseURL_Test
        val BaseUrl_Retrofit = "$BaseUrl/"

        //版本号

        //正常登录
        val NormalLogin = "$BaseUrl/commonapi/member_login"

        //扫码登录
        val ScanLogin = "$BaseUrl/commonapi/scan_login"

        val PersonInfo = "$BaseUrl/projectapi/member_show"

        val RoomShow="$BaseUrl/projectapi/room_show"

        val CodeGetRoom="$BaseUrl/projectapi/code_get_room"

        val ScanNurseInfo="$BaseUrl/projectapi/nurseShow"


        val CarTotal="$BaseUrl/projectapi/carTotal"

        val BagShow="$BaseUrl/bagapi/bag_show_code"

        val BagAdd="$BaseUrl/bagapi/bag_add"

        val RoomBagList="$BaseUrl/bagapi/room_bags_list"

        val EditBagCate="$BaseUrl/bagapi/bag_category_edit"

        val EditBagWeight="$BaseUrl/bagapi/bag_weight_edit"

        val EditBagQua="$BaseUrl/bagapi/bag_quality_edit"

        val OneBagPrint="$BaseUrl/bagapi/bag_print_one"

        val RoomBagPrint="$BaseUrl/bagapi/bag_print_room"

        val RoomBagSign="$BaseUrl/bagapi/bag_sign_room"

        val BatchNumber="$BaseUrl/projectapi/batchNum"

        val IsBox="$BaseUrl/bagapi/isBox"

        val PackBag="$BaseUrl/bagapi/bag_pack"


    }
}