package cn.ruanyun.backInterface.modules.business.goodsPackage.serviceimpl;

import cn.ruanyun.backInterface.common.enums.*;
import cn.ruanyun.backInterface.common.utils.*;
import cn.ruanyun.backInterface.common.vo.Result;
import cn.ruanyun.backInterface.modules.base.mapper.mapper.UserMapper;
import cn.ruanyun.backInterface.modules.base.pojo.User;
import cn.ruanyun.backInterface.modules.base.serviceimpl.mybatis.IUserServiceImpl;
import cn.ruanyun.backInterface.modules.base.vo.BackUserVO;
import cn.ruanyun.backInterface.modules.business.bookingOrder.service.IBookingOrderService;
import cn.ruanyun.backInterface.modules.business.discountCoupon.VO.DiscountCouponListVO;
import cn.ruanyun.backInterface.modules.business.discountCoupon.pojo.DiscountCoupon;
import cn.ruanyun.backInterface.modules.business.discountCoupon.service.IDiscountCouponService;
import cn.ruanyun.backInterface.modules.business.discountCoupon.serviceimpl.IDiscountCouponServiceImpl;
import cn.ruanyun.backInterface.modules.business.followAttention.service.IFollowAttentionService;
import cn.ruanyun.backInterface.modules.business.good.mapper.GoodMapper;
import cn.ruanyun.backInterface.modules.business.good.pojo.Good;
import cn.ruanyun.backInterface.modules.business.good.serviceimpl.IGoodServiceImpl;
import cn.ruanyun.backInterface.modules.business.goodsIntroduce.service.IGoodsIntroduceService;
import cn.ruanyun.backInterface.modules.business.goodsPackage.DTO.ShopParticularsDTO;
import cn.ruanyun.backInterface.modules.business.goodsPackage.VO.*;
import cn.ruanyun.backInterface.modules.business.goodsPackage.mapper.GoodsPackageMapper;
import cn.ruanyun.backInterface.modules.business.goodsPackage.pojo.GoodsPackage;
import cn.ruanyun.backInterface.modules.business.goodsPackage.service.IGoodsPackageService;
import cn.ruanyun.backInterface.modules.business.grade.service.IGradeService;
import cn.ruanyun.backInterface.modules.business.myFavorite.service.IMyFavoriteService;
import cn.ruanyun.backInterface.modules.business.storeAudit.service.IStoreAuditService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 商品套餐接口实现
 * @author fei
 */
@Slf4j
@Service
@Transactional
public class IGoodsPackageServiceImpl extends ServiceImpl<GoodsPackageMapper, GoodsPackage> implements IGoodsPackageService {


    @Autowired
    private SecurityUtil securityUtil;
    @Resource
    private UserMapper userMapper;
    @Resource
    private IUserServiceImpl iUserService;
    @Resource
    private GoodsPackageMapper igoodsPackageMapper;
    @Autowired
    private IStoreAuditService storeAuditService;
    @Resource
    private IDiscountCouponService iDiscountCouponService;
    @Resource
    private IDiscountCouponServiceImpl iDiscountCouponServiceImpl;
    @Resource
    private IGoodServiceImpl iGoodService;
    @Resource
    private IFollowAttentionService followAttentionService;
    @Resource
    private IBookingOrderService iBookingOrderService;
    @Resource
    private GoodMapper goodMapper;
    @Autowired
    private IGradeService gradeService;
    @Autowired
    private IGoodsIntroduceService iGoodsIntroduceService;
    @Autowired
    private IMyFavoriteService iMyFavoriteService;




    /**
     * App查询商家商品详情
     *
     * @param ids
     * @return
     */
    @Override
    public Result<Object> GetGoodsPackage(String ids) {
        Good goodsPackage = goodMapper.selectById(ids);

        GoodsPackageParticularsVO goodsPackageParticularsVO = new GoodsPackageParticularsVO();
           if(ToolUtil.isNotEmpty(goodsPackage)){
               goodsPackageParticularsVO.setId(goodsPackage.getId())
               .setGoodsName(goodsPackage.getGoodName())//商品名称
               .setPics(goodsPackage.getGoodPics())//套餐图片
               .setNewPrice(goodsPackage.getGoodNewPrice())//新价格
               .setOldPrice(goodsPackage.getGoodOldPrice())//旧价格
               .setMyFavorite(iMyFavoriteService.getMyFavorite(goodsPackage.getId(),GoodTypeEnum.GOODSPACKAGE))//是否收藏套餐
               .setProductsIntroduction(iGoodsIntroduceService.goodsIntroduceList(null,goodsPackage.getId(),1))//商品介绍
               .setPurchaseNotes(iGoodsIntroduceService.goodsIntroduceList(null,goodsPackage.getId(),2))//购买须知
               .setStoreAuditVO(storeAuditService.getStoreAudisByid(goodsPackage.getCreateBy()))//商铺信息
               ;

           }

        if(ToolUtil.isNotEmpty(goodsPackageParticularsVO.getId())){
            return new ResultUtil<>().setData(goodsPackageParticularsVO);
        }else {
            return null;
        }

    }


    /**
     * App分类商家商品筛选
     */
    @Override
    public List<GoodsPackageListVO> GetGoodsPackageList(String classId, String areaId, Integer newPrice, String createBy){
        List<GoodsPackage> list= this.list(new QueryWrapper<GoodsPackage>().lambda()
                .eq(EmptyUtil.isNotEmpty(classId),GoodsPackage::getClassId,classId)
                .eq(EmptyUtil.isNotEmpty(areaId),GoodsPackage::getAreaId,areaId)
                .eq(EmptyUtil.isNotEmpty(createBy),GoodsPackage::getCreateBy,createBy)
                .orderByDesc(EmptyUtil.isNotEmpty(newPrice)&&newPrice.equals(1),GoodsPackage::getNewPrice)
                .orderByAsc(EmptyUtil.isNotEmpty(newPrice)&&newPrice.equals(0),GoodsPackage::getNewPrice)
        );

        List<GoodsPackageListVO> goodsPackageListVOS = list.parallelStream().map(goodsPackage -> {
            GoodsPackageListVO goodsPackageListVOList =new GoodsPackageListVO();
            BackUserVO backUserVO = iUserService.getBackUserVO(goodsPackage.getCreateBy(), null);//查询用户信息
            goodsPackageListVOList.setUserid(goodsPackage.getCreateBy())
                    .setUserName(backUserVO.getUsername())
                    .setUserPic(backUserVO.getAvatar());
            ToolUtil.copyProperties(goodsPackage, goodsPackageListVOList);
            return goodsPackageListVOList;
        }).collect(Collectors.toList());
        return goodsPackageListVOS;
    }


    /**
     * 获取App店铺详情数据成功
     */
    @Override
    public ShopParticularsVO getShopParticulars(String ids, String longitude, String latitude){

        User user = iUserService.getById(ids);//获取店铺详情
        ShopParticularsVO shopParticularsVO = new ShopParticularsVO();
        if (ToolUtil.isNotEmpty(user)) {

            ToolUtil.copyProperties(user, shopParticularsVO);

            //获取店铺通用优惠券
            List<DiscountCoupon> discountCoupon = iDiscountCouponService.list(new QueryWrapper<DiscountCoupon>().lambda()
                    .eq(DiscountCoupon::getDisCouponType, DisCouponTypeEnum.ALL_USE)
                    .eq(DiscountCoupon::getStoreAuditOid, ids).eq(DiscountCoupon::getPastDue, BooleanTypeEnum.NO));

            List<DiscountCouponListVO> dvo = new ArrayList<>();
            for (DiscountCoupon dc : discountCoupon) {
                DiscountCouponListVO vo = new DiscountCouponListVO();
                ToolUtil.copyProperties(dc, vo);
                vo.setIsReceive(iDiscountCouponServiceImpl.getDetailById(dc));
                dvo.add(vo);
            }
            shopParticularsVO.setDiscountList(dvo);//优惠券

            //TODO::2020/4/13 店铺评分 未处理
            shopParticularsVO.setScore(gradeService.getShopScore(ids));
            //TODO::2020/4/13 是否关注店铺
            shopParticularsVO.setFavroite(followAttentionService.getMyFollowAttentionShop(ids, FollowTypeEnum.Follow_SHOP));
            //TODO::2020/4/13 是否预约店铺
            shopParticularsVO.setWhetherBookingOrder(iBookingOrderService.getWhetherBookingOrder(ids, securityUtil.getCurrUser().getId()));
            // TODO: 2020/4/20 距离
            shopParticularsVO.setDistance(LocationUtils.getDistance(longitude, latitude, user.getLongitude(), user.getLatitude()) + "");
            return shopParticularsVO;
        } else {
            return null;
        }


    }

    /**
     * 查询商家精选套餐
     */
    @Override
    public List<AppGoodsPackageListVO> AppGoodsPackageList(String ids,String name){

        List<Good>  goodsPackage = goodMapper.selectList(new QueryWrapper<Good>().lambda()
                .eq(ToolUtil.isNotEmpty(ids),Good::getCreateBy,ids)
                .like(ToolUtil.isNotEmpty(name),Good::getGoodName,name)
                .eq(Good::getTypeEnum,GoodTypeEnum.GOODSPACKAGE));

        List<AppGoodsPackageListVO> appGoodsPackageList = new ArrayList<>();

        for (Good gPackage : goodsPackage) {
            AppGoodsPackageListVO appGoodsVO = new AppGoodsPackageListVO();
            appGoodsVO.setId(gPackage.getId()).setNickName(Optional.ofNullable(userMapper.selectById(gPackage.getCreateBy())).map(User::getNickName).orElse(null))
            .setGoodsName(gPackage.getGoodName()).setNewPrice(gPackage.getGoodNewPrice()).setOldPrice(gPackage.getGoodOldPrice());
            String[] pic  = gPackage.getGoodPics().split(",");
            if(ToolUtil.isNotEmpty(pic)){
                appGoodsVO.setPics(pic[0]);
            }
            appGoodsPackageList.add(appGoodsVO);
        }
        return appGoodsPackageList;
    }

    @Override
    public List AppGoodsRecommendPackageList(String ids) {

        List<Good>  goodsPackage = goodMapper.selectList(new QueryWrapper<Good>().lambda()
                .eq(ToolUtil.isNotEmpty(ids),Good::getCreateBy,ids)
                .eq(Good::getTypeEnum,GoodTypeEnum.GOODSPACKAGE)
        );

        List<AppGoodsPackageListVO> appGoodsPackageList = new ArrayList<>();

        for (Good gPackage : goodsPackage) {
            AppGoodsPackageListVO appGoodsVO = new AppGoodsPackageListVO();
            appGoodsVO.setId(gPackage.getId()).setNickName(Optional.ofNullable(userMapper.selectById(gPackage.getCreateBy())).map(User::getNickName).orElse(null))
                    .setShopId(gPackage.getCreateBy())
                    .setGoodsName(gPackage.getGoodName()).setNewPrice(gPackage.getGoodNewPrice()).setOldPrice(gPackage.getGoodOldPrice());
            String[] pic  = gPackage.getGoodPics().split(",");
            if(ToolUtil.isNotEmpty(pic)){
                appGoodsVO.setPics(pic[0]);
            }
            appGoodsPackageList.add(appGoodsVO);
        }
        Collections.shuffle(appGoodsPackageList);
        return appGoodsPackageList;
    }

    /**
     * 修改店铺详情
     */
    @Override
    public void UpdateShopParticulars(ShopParticularsDTO shopParticularsDTO){
        igoodsPackageMapper.UpdateShopParticulars(shopParticularsDTO);
    }

    /**
     * 后端获取店铺列表
     */
    @Override
    public List<ShopDatelistVO> getShopDateList(String username, String shopName, Integer storeType) {
//            return igoodsPackageMapper.getShopDateList(username,shopName,storeType);r
        return null;
    }

    /**
     * 获取App店铺详情参数
     */
    @Override
    public ShopParticularsParameterVO getShopParticularsParameter(String ids) {

        User user = iUserService.getById(ids);
        if(ToolUtil.isNotEmpty(user)){
            ShopParticularsParameterVO shopParticularsParameterVO  = new ShopParticularsParameterVO();
            shopParticularsParameterVO.setId(user.getId())
                    .setAvatar(user.getAvatar())
                    .setShopName(user.getShopName())
                    .setIndividualResume(user.getIndividualResume())
                    .setGoodsNum(iGoodService.getAppForSaleGoods(ids).size())//获取商品数量
                    .setFollowAttentionNum(followAttentionService.getMefansNum(ids))//获取店铺关注数量
                    //TODO::评论数量 暂无
                     .setEvaluateNum(null)
                    .setMyFollowAttention(followAttentionService.getMyFollowAttentionShop(ids,FollowTypeEnum.Follow_SHOP))//当前登录用户是否关注这个店铺
            ;
                if(ToolUtil.isNotEmpty(user.getPic())){
                    String[] pic = user.getPic().split(",");
                    shopParticularsParameterVO.setPic(pic[0]);
                }


            return shopParticularsParameterVO;
        }else {
            return null;
        }

    }


}