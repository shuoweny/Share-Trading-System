"use strict";(self["webpackChunktrading_system"]=self["webpackChunktrading_system"]||[]).push([[22],{7896:function(e,t,o){o.d(t,{Z:function(){return c}});var r=o(4708),i=(o(3241),o(1010),o(5659)),n=(o(444),o(363),o(6252));const a={class:"container"};function s(e,t,o,s,l,u){const p=r.$Y,c=i.ElButton,d=r.eI;return(0,n.wg)(),(0,n.iD)("div",a,[(0,n.Wm)(d,{data:o.companyListings,style:{width:"100%"}},{default:(0,n.w5)((()=>[(0,n.Wm)(p,{prop:"listingid",label:"Listing ID"}),(0,n.Wm)(p,{prop:"companyname",label:"Company Name"}),"Delete"!==o.displayType?((0,n.wg)(),(0,n.j4)(p,{key:0,prop:"category",label:"Category"})):(0,n.kq)("",!0),(0,n.Wm)(p,{prop:"shareType",label:"Share Type"}),(0,n.Wm)(p,{prop:"numShare",label:"Amount"}),(0,n.Wm)(p,{prop:"price",label:"Price($)"}),"Buy"==o.displayType?((0,n.wg)(),(0,n.j4)(p,{key:1,label:"Purchase"},{default:(0,n.w5)((e=>[(0,n.Wm)(c,{type:"primary",onClick:t=>u.buyShare(e.row.listingid)},{default:(0,n.w5)((()=>[(0,n.Uk)("Buy")])),_:2},1032,["onClick"])])),_:1})):(0,n.kq)("",!0),"Sell"==o.displayType?((0,n.wg)(),(0,n.j4)(p,{key:2,label:"Sell"},{default:(0,n.w5)((e=>[(0,n.Wm)(c,{type:"primary",onClick:t=>u.sellShare(e.row.listingid)},{default:(0,n.w5)((()=>[(0,n.Uk)("Sell")])),_:2},1032,["onClick"])])),_:1})):(0,n.kq)("",!0),"Edit"==o.displayType?((0,n.wg)(),(0,n.j4)(p,{key:3,label:"Edit"},{default:(0,n.w5)((e=>[(0,n.Wm)(c,{type:"primary",onClick:t=>u.editListing(e.row)},{default:(0,n.w5)((()=>[(0,n.Uk)("Edit")])),_:2},1032,["onClick"])])),_:1})):(0,n.kq)("",!0)])),_:1},8,["data"])])}var l={props:{companyListings:{type:Array,default:()=>[]},displayType:{type:String,default:null}},methods:{buyShare(e){this.$emit("buy-share",e)},sellShare(e){this.$emit("sell-share",e)},editListing(e){this.$emit("edit-listing",e)}}},u=o(3744);const p=(0,u.Z)(l,[["render",s],["__scopeId","data-v-82fe6df2"]]);var c=p},4940:function(e,t,o){o.d(t,{Z:function(){return h}});var r=o(4708),i=(o(3241),o(1010),o(5659)),n=(o(444),o(363),o(6252)),a=o(3577);const s={class:"portfolio-component"},l=(0,n._)("h2",null,"Portfolio",-1);function u(e,t,o,u,p,c){const d=r.$Y,m=i.ElButton,f=r.eI;return(0,n.wg)(),(0,n.iD)("div",s,[l,(0,n._)("h3",null,"Net Profit is "+(0,a.zw)(-p.netProfit),1),(0,n.Wm)(f,{data:p.portfolioData},{default:(0,n.w5)((()=>[(0,n.kq)("",!0),(0,n.Wm)(d,{prop:"companyName",label:"Company Name"}),(0,n.Wm)(d,{prop:"shareType",label:"Share Type"}),(0,n.Wm)(d,{prop:"numShare",label:"Amount"}),(0,n.Wm)(d,{prop:"purchasePrice",label:"Purchase Price"}),(0,n.Wm)(d,{prop:"currentPrice",label:"Current Price"}),(0,n.Wm)(d,{prop:"profit",label:"Profit"}),"User"===o.displayType?((0,n.wg)(),(0,n.j4)(d,{key:1,label:"Actions"},{default:(0,n.w5)((e=>[(0,n.Wm)(m,{type:"primary",onClick:t=>c.sellShare(e.row.listingid)},{default:(0,n.w5)((()=>[(0,n.Uk)("Sell")])),_:2},1032,["onClick"])])),_:1})):(0,n.kq)("",!0)])),_:1},8,["data"])])}var p=o(7896),c=o(2104),d=o(1348),m={components:{CompanyListings:p.Z},props:{userId:{type:Number,default:null},displayType:{type:String,default:"User"},isUpdate:{type:Boolean,default:!1}},data(){return{portfolioData:[],netProfit:0}},methods:{async getPortfolioDataAndNetProfit(){console.log("getting user info, id : "+this.userId);var e=this;try{const o=await c.Z.getPortfolioData(this.userId);if("success"===o.data.status){console.log(o.data.portfolioDetails);var t=this.processRawPortfolioData(o.data.portfolioDetails);e.portfolioData=t.portfolioData,e.netProfit=t.profit}else d.z8.error("Resource Not Found")}catch(o){console.error("Error fetching portfolio data",o),d.z8.error("Error fetching data")}},processRawPortfolioData(e){console.log("processing portfolio data");var t=[],o=0;for(var r of e)console.log(r),t.push({listingid:r.listingid,companyName:r.companyName,shareType:r.shareType,numShare:r.numShare,purchasePrice:r.price,currentPrice:r.price-r.profit/r.numShare,profit:r.profit}),o-=r.profit;return{portfolioData:t,profit:o}},sellShare(e){console.log("Emiting Event"),console.log(e),this.$emit("sell-share",e)}},created(){this.getPortfolioDataAndNetProfit()}},f=o(3744);const y=(0,f.Z)(m,[["render",u]]);var h=y},6022:function(e,t,o){o.r(t),o.d(t,{default:function(){return _}});var r=o(4940),i=o(7896),n=o(984),a=(o(3241),o(4334),o(632),o(6252));const s=(0,a._)("h1",null,"Administrator Dashboard",-1),l={key:0},u={key:1},p={key:2};function c(e,t,o,c,d,m){const f=n.E_,y=n.Q8,h=(0,a.up)("AllCustomers"),g=i.Z,w=r.Z;return(0,a.wg)(),(0,a.iD)("div",null,[s,(0,a.Wm)(y,{mode:"horizontal"},{default:(0,a.w5)((()=>[(0,a.Wm)(f,{index:"1",onClick:t[0]||(t[0]=e=>m.switchMenu(1))},{default:(0,a.w5)((()=>[(0,a.Uk)("All Customers")])),_:1}),(0,a.Wm)(f,{index:"2",onClick:t[1]||(t[1]=e=>m.switchMenu(2))},{default:(0,a.w5)((()=>[(0,a.Uk)("All Company Listings")])),_:1}),3===d.menuIndex?((0,a.wg)(),(0,a.j4)(f,{key:0,index:"3"},{default:(0,a.w5)((()=>[(0,a.Uk)("Customer Portfolio")])),_:1})):(0,a.kq)("",!0)])),_:1}),1===d.menuIndex?((0,a.wg)(),(0,a.iD)("h2",l,"All Customers")):(0,a.kq)("",!0),2===d.menuIndex?((0,a.wg)(),(0,a.iD)("h2",u,"All Company Listings")):(0,a.kq)("",!0),3===d.menuIndex?((0,a.wg)(),(0,a.iD)("h2",p,"Cutomer Portfolio Details")):(0,a.kq)("",!0),1===d.menuIndex?((0,a.wg)(),(0,a.j4)(h,{key:3,customers:d.customers,onViewPortfolio:m.viewPortfolio},null,8,["customers","onViewPortfolio"])):(0,a.kq)("",!0),2===d.menuIndex?((0,a.wg)(),(0,a.j4)(g,{key:4,companyListings:d.companyListings},null,8,["companyListings"])):(0,a.kq)("",!0),3===d.menuIndex?((0,a.wg)(),(0,a.j4)(w,{key:5,userId:this.currentViewingCid,displayType:"Admin"},null,8,["userId"])):(0,a.kq)("",!0)])}var d=o(4708),m=(o(1010),o(5659));o(444),o(363);const f={class:"container"};function y(e,t,o,r,i,n){const s=d.$Y,l=m.ElButton,u=d.eI;return(0,a.wg)(),(0,a.iD)("div",f,[(0,a.Wm)(u,{data:o.customers,style:{width:"100%"}},{default:(0,a.w5)((()=>[(0,a.Wm)(s,{prop:"userid",label:"UserId"}),(0,a.Wm)(s,{prop:"username",label:"Username"}),(0,a.Wm)(s,{label:"Action"},{default:(0,a.w5)((e=>[(0,a.Wm)(l,{type:"primary",onClick:t=>n.viewPortfolio(e.row.userid)},{default:(0,a.w5)((()=>[(0,a.Uk)("View Portfolio")])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["data"])])}var h={props:{customers:{type:Array,default:()=>[]}},methods:{viewPortfolio(e){this.$emit("view-portfolio",e)}}},g=o(3744);const w=(0,g.Z)(h,[["render",y],["__scopeId","data-v-7f28a8a0"]]);var k=w,C=o(2104),P=o(1348),v={components:{AllCustomers:k,CompanyListings:i.Z,PortfolioComponent:r.Z},data(){return{menuIndex:1,customers:[],companyListings:[],currentViewingCid:null}},methods:{switchMenu(e){this.menuIndex=e},viewPortfolio(e){console.log("View portfolio of customer with ID:",e),this.currentViewingCid=e,this.switchMenu(3)},fetchCustomers(){var e=this;C.Z.getAllCustomers().then((t=>{"success"===t.data.status?e.customers=t.data.customers:P.z8.error("Resourse Not Found Error")}))},fetchAllCompanyListings(){var e=this;C.Z.getCompanyListings("Admin",null,null,null).then((t=>{"success"===t.data.status?(console.log(t.data),e.companyListings=t.data.companyListings):(P.z8.error("Resourse Not Found Error"),e.$router.go(0))}))}},beforeMount(){this.fetchCustomers(),this.fetchAllCompanyListings()}};const b=(0,g.Z)(v,[["render",c]]);var _=b},5508:function(e,t,o){o(3241)},632:function(e,t,o){o(3241)},4334:function(e,t,o){o(3241),o(5095)},363:function(e,t,o){o(3241),o(5508),o(2471)},1010:function(e,t,o){o(3241),o(5508),o(5095),o(9290)},5095:function(e,t,o){o(3241),o(5392)}}]);
//# sourceMappingURL=22.2a522165.js.map