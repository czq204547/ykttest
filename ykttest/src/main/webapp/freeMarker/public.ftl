<!DOCTYPE html>
<html>
<#include "include/head.ftl">
<body>
<#include "include/support.ftl">
<#include "include/header.ftl">
<div class="g-doc">
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <h2>内容发布</h2>
    </div>
    <div class="n-public">
        <form class="m-form m-form-ht" id="form" method="post" action="/publicSubmit"  enctype="multipart/form-data" autocomplete="off">
            <div class="fmitem">
                <label class="fmlab">标题：</label>
                <div class="fmipt">
                    <input class="u-ipt ipt" name="title" autofocus placeholder="2-80字符"/>
                </div>
            </div>
            <div class="fmitem">
                <label class="fmlab">摘要：</label>
                <div class="fmipt">
                    <input class="u-ipt ipt" name="summary" placeholder="2-140字符"/>
                </div>
            </div>

            <div class="fmitem">
                <label class="fmlab">图片：</label>
                <div class="fmipt">
                    <input type="radio" value="1" name="upload" id="r1" checked>图片地址
                    <input type="radio" value="2" name="upload" id="r2">本地上传
                </div>
            </div>

            <div  class="fmitem">
                <label class="fmlab"></label>
                <div id="img1" class="fmipt" >
                    <input  class="u-ipt ipt" type="text" name="image" id="url" placeholder="图片地址">
                </div>

                <div id="img2" class="fmipt">
                    <input class="u-ipt ipt" type="file" name="file" id="pic">
                </div>
            </div>



            <div class="fmitem">
                <label class="fmlab">正文：</label>
                <div class="fmipt">
                    <textarea class="u-ipt" name="detail" rows="10" placeholder="2-1000个字符"></textarea>
                </div>
            </div>
            <div class="fmitem">
                <label class="fmlab">价格：</label>
                <div class="fmipt">
                    <input class="u-ipt price" name="price"/>元
                </div>
            </div>
            <div class="fmitem fmitem-nolab fmitem-btn">
                <div class="fmipt">
                    <button type="submit" class="u-btn u-btn-primary u-btn-lg">发 布</button>
                </div>
            </div>
        </form>
        <span class="imgpre"><img src="" alt="" id="imgpre"></span>
    </div>
</div>
<#include "include/footer.ftl">
<script src="../js/jquery-3.2.1.min.js"></script>
<script>
    $(function () {
        $("#img2").hide();
        $("#r1").click(function () {
            $("#img1").show();
            $("#img2").hide();
        })
        $("#r2").click(function () {
            $("#img1").hide();
            $("#img2").show();
        })
    });
</script>
</body>
</html>