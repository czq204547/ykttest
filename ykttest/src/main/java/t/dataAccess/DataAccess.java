package t.dataAccess;

import org.apache.ibatis.annotations.*;
import t.po.Content;
import t.po.User;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */
//访问数据库接口
public interface DataAccess {
    //登录验证/api/login
    @Select("select id,userName,userType,nickName from person where userName=#{userName} && password=#{password}")
    User login(User user);

    //发布商品/public
    @Insert("insert into content (price,title,icon,abstract,text) values (#{price},#{title}," +
            "#{image},#{summary},#{detail})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int saveContent(Content content);

    //查看商品/show
    @Select("select * from content where id=#{id}")
    @Results({
            @Result(column = "icon",property = "image"),
            @Result(column = "abstract",property = "summary"),
            @Result(column = "text",property = "detail")
    })
    Content show(int id);

    //删除商品/api/delete
    @Delete("delete from content where id=#{id}")
    void deleteContentById(int id);

    //查询所有商品/index
    @Select("select * from content")
    @Results({
            @Result(column = "icon",property = "image"),
            @Result(column = "abstract",property = "summary"),
            @Result(column = "text",property = "detail")
    })
    List<Content> index();

    //edit商品
    @Update("update content set price=#{price},title=#{title},icon=#{image},abstract=#{summary},text=#{detail} where id=#{id}")
    void editContent(Content content);

    //trx购买商品保存到trx表/api/buy
    @Insert("insert into trx (contentId,price,time,num,personId) values(#{id},#{price},#{buyTime},#{number},#{personId})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int saveTrx(Content content);

    //trx查询交易表,查询某个买家购买的所有商品
    @Select("select contentId,price,time,num from trx where personId=#{personId}")
    @Results({
            @Result(column = "contentId",property = "id"),
            @Result(column = "price",property = "buyPrice"),
            @Result(column = "time",property = "buyTime"),
            @Result(column = "num",property = "buyNum")
    })
    List<Content> selectTrx(int personId);

    //trx查询某个买家是否买走某个商品
    @Select("select count(*) from trx where contentId=#{id} && personId=#{personId}")
    int selectById(Content content);

    //trx表查询某件商品卖出的数量
    @Select("select sum(num) from trx where contentId=#{id}")
    Integer selectByIdSaleNum(Content content);

    //trx表查寻买家购买的某件商品当时价格、时间、数量
    @Select("select price,time,num from trx where contentId=#{id} && personId=#{personId}")
    @Results({
            @Result(column = "price",property = "buyPrice"),
            @Result(column = "time",property = "buyTime"),
            @Result(column = "num",property = "buyNum")
    })
    Content selectTrxById(Content content);
}
