"use strict";(self["webpackChunktrading_system"]=self["webpackChunktrading_system"]||[]).push([[576],{7896:function(e,a,l){l.d(a,{Z:function(){return c}});var t=l(4708),o=(l(3241),l(1010),l(5659)),i=(l(444),l(363),l(6252));const s={class:"container"};function r(e,a,l,r,n,u){const m=t.$Y,c=o.ElButton,p=t.eI;return(0,i.wg)(),(0,i.iD)("div",s,[(0,i.Wm)(p,{data:l.companyListings,style:{width:"100%"}},{default:(0,i.w5)((()=>[(0,i.Wm)(m,{prop:"listingid",label:"Listing ID"}),(0,i.Wm)(m,{prop:"companyname",label:"Company Name"}),"Delete"!==l.displayType?((0,i.wg)(),(0,i.j4)(m,{key:0,prop:"category",label:"Category"})):(0,i.kq)("",!0),(0,i.Wm)(m,{prop:"shareType",label:"Share Type"}),(0,i.Wm)(m,{prop:"numShare",label:"Amount"}),(0,i.Wm)(m,{prop:"price",label:"Price($)"}),"Buy"==l.displayType?((0,i.wg)(),(0,i.j4)(m,{key:1,label:"Purchase"},{default:(0,i.w5)((e=>[(0,i.Wm)(c,{type:"primary",onClick:a=>u.buyShare(e.row.listingid)},{default:(0,i.w5)((()=>[(0,i.Uk)("Buy")])),_:2},1032,["onClick"])])),_:1})):(0,i.kq)("",!0),"Sell"==l.displayType?((0,i.wg)(),(0,i.j4)(m,{key:2,label:"Sell"},{default:(0,i.w5)((e=>[(0,i.Wm)(c,{type:"primary",onClick:a=>u.sellShare(e.row.listingid)},{default:(0,i.w5)((()=>[(0,i.Uk)("Sell")])),_:2},1032,["onClick"])])),_:1})):(0,i.kq)("",!0),"Edit"==l.displayType?((0,i.wg)(),(0,i.j4)(m,{key:3,label:"Edit"},{default:(0,i.w5)((e=>[(0,i.Wm)(c,{type:"primary",onClick:a=>u.editListing(e.row)},{default:(0,i.w5)((()=>[(0,i.Uk)("Edit")])),_:2},1032,["onClick"])])),_:1})):(0,i.kq)("",!0)])),_:1},8,["data"])])}var n={props:{companyListings:{type:Array,default:()=>[]},displayType:{type:String,default:null}},methods:{buyShare(e){this.$emit("buy-share",e)},sellShare(e){this.$emit("sell-share",e)},editListing(e){this.$emit("edit-listing",e)}}},u=l(3744);const m=(0,u.Z)(n,[["render",r],["__scopeId","data-v-82fe6df2"]]);var c=m},4940:function(e,a,l){l.d(a,{Z:function(){return y}});var t=l(4708),o=(l(3241),l(1010),l(5659)),i=(l(444),l(363),l(6252)),s=l(3577);const r={class:"portfolio-component"},n=(0,i._)("h2",null,"Portfolio",-1);function u(e,a,l,u,m,c){const p=t.$Y,d=o.ElButton,h=t.eI;return(0,i.wg)(),(0,i.iD)("div",r,[n,(0,i._)("h3",null,"Net Profit is "+(0,s.zw)(-m.netProfit),1),(0,i.Wm)(h,{data:m.portfolioData},{default:(0,i.w5)((()=>[(0,i.kq)("",!0),(0,i.Wm)(p,{prop:"companyName",label:"Company Name"}),(0,i.Wm)(p,{prop:"shareType",label:"Share Type"}),(0,i.Wm)(p,{prop:"numShare",label:"Amount"}),(0,i.Wm)(p,{prop:"purchasePrice",label:"Purchase Price"}),(0,i.Wm)(p,{prop:"currentPrice",label:"Current Price"}),(0,i.Wm)(p,{prop:"profit",label:"Profit"}),"User"===l.displayType?((0,i.wg)(),(0,i.j4)(p,{key:1,label:"Actions"},{default:(0,i.w5)((e=>[(0,i.Wm)(d,{type:"primary",onClick:a=>c.sellShare(e.row.listingid)},{default:(0,i.w5)((()=>[(0,i.Uk)("Sell")])),_:2},1032,["onClick"])])),_:1})):(0,i.kq)("",!0)])),_:1},8,["data"])])}var m=l(7896),c=l(2104),p=l(1348),d={components:{CompanyListings:m.Z},props:{userId:{type:Number,default:null},displayType:{type:String,default:"User"},isUpdate:{type:Boolean,default:!1}},data(){return{portfolioData:[],netProfit:0}},methods:{async getPortfolioDataAndNetProfit(){console.log("getting user info, id : "+this.userId);var e=this;try{const l=await c.Z.getPortfolioData(this.userId);if("success"===l.data.status){console.log(l.data.portfolioDetails);var a=this.processRawPortfolioData(l.data.portfolioDetails);e.portfolioData=a.portfolioData,e.netProfit=a.profit}else p.z8.error("Resource Not Found")}catch(l){console.error("Error fetching portfolio data",l),p.z8.error("Error fetching data")}},processRawPortfolioData(e){console.log("processing portfolio data");var a=[],l=0;for(var t of e)console.log(t),a.push({listingid:t.listingid,companyName:t.companyName,shareType:t.shareType,numShare:t.numShare,purchasePrice:t.price,currentPrice:t.price-t.profit/t.numShare,profit:t.profit}),l-=t.profit;return{portfolioData:a,profit:l}},sellShare(e){console.log("Emiting Event"),console.log(e),this.$emit("sell-share",e)}},created(){this.getPortfolioDataAndNetProfit()}},h=l(3744);const f=(0,h.Z)(d,[["render",u]]);var y=f},9576:function(e,a,l){l.r(a),l.d(a,{default:function(){return M}});var t=l(3481),o=(l(3241),l(677),l(5659)),i=(l(444),l(5836)),s=(l(9840),l(6589),l(5841)),r=(l(4367),l(4940)),n=l(7896),u=l(3034),m=(l(986),l(1715),l(6252));const c={class:"search-component"},p=(0,m._)("h2",null,"Search For Share",-1),d=(0,m._)("span",null,"Company Name",-1),h=(0,m._)("span",null,"Industry / Category",-1),f=(0,m._)("span",null,"Share Type",-1),y=(0,m._)("span",null,"Price Range",-1),g=(0,m._)("p",null,"Minimum",-1),b=(0,m._)("p",null,"Maximum",-1),w=(0,m._)("h2",null,"Search Result",-1);function S(e,a,l,t,i,r){const S=s.EZ,k=u.BT,P=u.ElSelect,_=o.ElButton,W=n.Z;return(0,m.wg)(),(0,m.iD)("div",c,[p,d,(0,m.Wm)(S,{placeholder:"Company Name",modelValue:t.filters.companyName,"onUpdate:modelValue":a[0]||(a[0]=e=>t.filters.companyName=e)},null,8,["modelValue"]),h,(0,m.Wm)(S,{placeholder:"Industry",modelValue:t.filters.industry,"onUpdate:modelValue":a[1]||(a[1]=e=>t.filters.industry=e)},null,8,["modelValue"]),f,(0,m.Wm)(P,{modelValue:t.filters.shareType,"onUpdate:modelValue":a[2]||(a[2]=e=>t.filters.shareType=e),placeholder:"Select Type",size:"large"},{default:(0,m.w5)((()=>[(0,m.Wm)(k,{label:"A",value:"A"}),(0,m.Wm)(k,{label:"B",value:"B"}),(0,m.Wm)(k,{label:"C",value:"C"}),(0,m.Wm)(k,{label:"D",value:"D"})])),_:1},8,["modelValue"]),y,g,(0,m.Wm)(S,{placeholder:"Minimum Price",modelValue:t.filters.priceRange.minimum,"onUpdate:modelValue":a[3]||(a[3]=e=>t.filters.priceRange.minimum=e)},null,8,["modelValue"]),b,(0,m.Wm)(S,{placeholder:"Maximum Price",modelValue:t.filters.priceRange.maximum,"onUpdate:modelValue":a[4]||(a[4]=e=>t.filters.priceRange.maximum=e)},null,8,["modelValue"]),(0,m.Wm)(_,{type:"primary",onClick:t.searchShares},{default:(0,m.w5)((()=>[(0,m.Uk)("Search")])),_:1},8,["onClick"]),w,(0,m.Wm)(W,{class:"result",companyListings:t.searchResults,displayType:"Buy",onBuyShare:t.buyShare},null,8,["companyListings","onBuyShare"])])}var k=l(2262),P=l(2257);const _={filterListings(e){var a=P.Z.getters.getAllCompanyListings;return a.filter((a=>(!e.companyName||a.companyname===e.companyName)&&((!e.industry||a.category===e.industry)&&((!e.shareType||a.shareType===e.shareType)&&(!(null!==e.priceRange.minimum&&a.price<e.priceRange.minimum)&&!(null!==e.priceRange.maximum&&a.price>e.priceRange.maximum))))))}};var W=_,D={components:{CompanyListings:n.Z},setup(e,a){const l=(0,k.iH)({companyName:"",industry:"",shareType:"",priceRange:{minimum:null,maximum:null}}),t=(0,k.iH)([]),o=(0,k.iH)(""),i=()=>{console.log("search cirteria "),console.log(l.value),t.value=W.filterListings(l.value),l.value={companyName:"",industry:"",shareType:"",priceRange:{minimum:null,maximum:null}}},s=e=>{a.emit("buy-share",e)};return{filters:l,searchResults:t,purchaseAmount:o,searchShares:i,buyShare:s}}},C=l(3744);const I=(0,C.Z)(D,[["render",S]]);var V=I,U=l(984),v=(l(4334),l(632),l(3577));const F={id:"customer-dashboard"},T=(0,m._)("h1",null,"Customer Dashboard",-1),B={key:0},L={class:"dialog-footer"},x={class:"dialog-footer"};function Z(e,a,l,u,c,p){const d=U.E_,h=U.Q8,f=n.Z,y=V,g=r.Z,b=s.EZ,w=i.nH,S=i.ly,k=o.ElButton,P=t.d0;return(0,m.wg)(),(0,m.iD)("div",F,[T,(0,m._)("h3",null,"Hi "+(0,v.zw)(this.$route.params.username),1),(0,m._)("h4",null,"Current Balance is "+(0,v.zw)(this.balance),1),(0,m.Wm)(h,{mode:"horizontal"},{default:(0,m.w5)((()=>[(0,m.Wm)(d,{index:"1",onClick:a[0]||(a[0]=e=>p.switchMenu(1))},{default:(0,m.w5)((()=>[(0,m.Uk)("Market")])),_:1}),(0,m.Wm)(d,{index:"2",onClick:a[1]||(a[1]=e=>p.switchMenu(2))},{default:(0,m.w5)((()=>[(0,m.Uk)("Search")])),_:1}),(0,m.Wm)(d,{index:"3",onClick:a[2]||(a[2]=e=>p.switchMenu(3))},{default:(0,m.w5)((()=>[(0,m.Uk)("Portfolio")])),_:1})])),_:1}),1===c.menuIndex?((0,m.wg)(),(0,m.iD)("h2",B,"Market Place")):(0,m.kq)("",!0),1===c.menuIndex?((0,m.wg)(),(0,m.j4)(f,{key:1,companyListings:c.marketData,displayType:"Buy",onBuyShare:p.openPurchaseDialog},null,8,["companyListings","onBuyShare"])):(0,m.kq)("",!0),2===c.menuIndex?((0,m.wg)(),(0,m.j4)(y,{key:2,onBuyShare:p.openPurchaseDialog},null,8,["onBuyShare"])):(0,m.kq)("",!0),3===c.menuIndex?((0,m.wg)(),(0,m.j4)(g,{key:3,userId:this.userInfo.userId,onSellShare:p.openSellDialog,isUpdate:c.isProfileUpdate},null,8,["userId","onSellShare","isUpdate"])):(0,m.kq)("",!0),(0,m.Wm)(P,{modelValue:c.isPurchaseDialogVisible,"onUpdate:modelValue":a[5]||(a[5]=e=>c.isPurchaseDialogVisible=e),title:"Purchase Share"},{footer:(0,m.w5)((()=>[(0,m._)("span",L,[(0,m.Wm)(k,{onClick:p.cancelPurchase},{default:(0,m.w5)((()=>[(0,m.Uk)("Cancel")])),_:1},8,["onClick"]),(0,m.Wm)(k,{type:"primary",onClick:a[4]||(a[4]=e=>p.confirmPurchase(c.purchaseForm.listId))},{default:(0,m.w5)((()=>[(0,m.Uk)(" Confirm ")])),_:1})])])),default:(0,m.w5)((()=>[(0,m.Wm)(S,{model:c.purchaseForm},{default:(0,m.w5)((()=>[(0,m._)("h4",null," Purchansing the listing "+(0,v.zw)(c.purchaseForm.listId),1),(0,m.Wm)(w,{label:"Amount","label-width":c.formLabelWidth},{default:(0,m.w5)((()=>[(0,m.Wm)(b,{modelValue:c.purchaseForm.amount,"onUpdate:modelValue":a[3]||(a[3]=e=>c.purchaseForm.amount=e),autocomplete:"off"},null,8,["modelValue"])])),_:1},8,["label-width"])])),_:1},8,["model"])])),_:1},8,["modelValue"]),(0,m.Wm)(P,{modelValue:c.isSellDialogVisible,"onUpdate:modelValue":a[8]||(a[8]=e=>c.isSellDialogVisible=e),title:"Sell Share"},{footer:(0,m.w5)((()=>[(0,m._)("span",x,[(0,m.Wm)(k,{onClick:p.cancelPurchase},{default:(0,m.w5)((()=>[(0,m.Uk)("Cancel")])),_:1},8,["onClick"]),(0,m.Wm)(k,{type:"primary",onClick:a[7]||(a[7]=e=>p.confirmSell(c.sellForm.listId))},{default:(0,m.w5)((()=>[(0,m.Uk)(" Confirm ")])),_:1})])])),default:(0,m.w5)((()=>[(0,m.Wm)(S,{model:c.sellForm},{default:(0,m.w5)((()=>[(0,m._)("h4",null," Sell the share "+(0,v.zw)(c.sellForm.listId),1),(0,m.Wm)(w,{label:"Amount","label-width":c.formLabelWidth},{default:(0,m.w5)((()=>[(0,m.Wm)(b,{modelValue:c.sellForm.amount,"onUpdate:modelValue":a[6]||(a[6]=e=>c.sellForm.amount=e),autocomplete:"off"},null,8,["modelValue"])])),_:1},8,["label-width"])])),_:1},8,["model"])])),_:1},8,["modelValue"])])}var N=l(2104),R=l(1348),E={components:{SearchComponent:V,PortfolioComponent:r.Z,CompanyListings:n.Z},data(){return{menuIndex:1,marketData:[],isPurchaseDialogVisible:!1,isSellDialogVisible:!1,purchaseForm:{listId:"",amount:""},sellForm:{listId:"",amount:""},formLabelWidth:"180px",userInfo:null,balance:0,isProfileUpdate:!1}},methods:{switchMenu(e){this.menuIndex=e},getUserInfo(){this.userInfo=this.$store.getters.getUserInfo},buyShare(e){console.log("Buy share "+e),console.log("Amount : "+this.purchaseForm.amount),console.log("start execute purchase logic");var a=this;N.Z.purchaseShare(this.userInfo.userId,this.purchaseForm).then((e=>{console.log(e),"success"===e.data.status?(a.$store.dispatch("updateBalance",e.data.balance),a.balance=e.data.balance,(0,R.z8)({message:e.data.message,type:"success"}),a.fetchMarketData()):R.z8.error(e.data.message)}))},sellShare(e){console.log("Sell share "),console.log(this.sellForm.listId),console.log("Amount : "+this.sellForm.amount),console.log("start execute sell logic");var a=this;N.Z.sellShare(this.sellForm).then((e=>{console.log(e),"success"===e.data.status?(a.$store.dispatch("updateBalance",e.data.balance),a.balance=e.data.balance,(0,R.z8)({message:e.data.message,type:"success"}),a.fetchMarketData()):R.z8.error(e.data.message)}))},openPurchaseDialog(e){this.isPurchaseDialogVisible=!0,this.purchaseForm.listId=e},confirmPurchase(e){this.buyShare(e),this.isPurchaseDialogVisible=!1,this.purchaseForm={listId:"",amount:""},this.isProfileUpdate=!this.isProfileUpdate},cancelPurchase(){this.isPurchaseDialogVisible=!1,this.purchaseForm={listId:"",amount:""}},openSellDialog(e){this.isSellDialogVisible=!0,this.sellForm.listId=e},confirmSell(e){this.sellShare(e),this.isSellDialogVisible=!1,this.sellForm={listId:"",amount:""},this.isProfileUpdate=!this.isProfileUpdate},cancelSell(){this.isSellDialogVisible=!1,this.sellForm={listId:"",amount:""}},fetchMarketData(){var e=this;N.Z.getCompanyListings("User",null,null,null).then((a=>{console.log(a),"success"===a.data.status?(console.log(a.data),e.marketData=a.data.companyListings,e.$store.dispatch("updateAllCompanyListings",a.data.companyListings)):(R.z8.error("Resourse Not Found Error"),e.$router.go(0))}))}},beforeMount(){this.getUserInfo(),this.fetchMarketData(),this.balance=this.$store.getters.getBalance},beforeCreate(){this.get}};const A=(0,C.Z)(E,[["render",Z]]);var M=A},5508:function(e,a,l){l(3241)},677:function(e,a,l){l(3241)},632:function(e,a,l){l(3241)},4334:function(e,a,l){l(3241),l(5095)},363:function(e,a,l){l(3241),l(5508),l(2471)},1010:function(e,a,l){l(3241),l(5508),l(5095),l(9290)},5095:function(e,a,l){l(3241),l(5392)}}]);
//# sourceMappingURL=576.af32e52d.js.map