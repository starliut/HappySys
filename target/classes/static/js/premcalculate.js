/**
* ------------------------------------------
* @make:崔世刚
* @version  1.0 
* @des：保费试算
* ------------------------------------------
*/

function DataCollection(){
	this.map = {};
	this.valuetype = {};
	this.keys = [];

	DataCollection.prototype.get = function(ID){
		if(typeof(ID)=="number"){
			return this.map[this.keys[ID]];
		}
		return this.map[ID];
	};

	DataCollection.prototype.getKey = function(index){
		return this.keys[index];
	};

	DataCollection.prototype.size = function(){
		return this.keys.length;
	};

	DataCollection.prototype.remove = function(ID){
		if(typeof(ID)=="number"){
			if(ID<this.keys.length){
				var obj = this.map[this.keys[ID]];
				this.map[this.keys[ID]] = null;
				this.keys.splice(ID);
				return obj;
			}
		}else{
			for(var i=0;i<this.keys.length;i++){
				if(this.keys[i]==ID){
					var obj = this.map[ID];
					this.map[ID] = null;
					this.keys.splice(i);
					return obj;
					break;
				}
			}
		}
		return null;
	};

	DataCollection.prototype.toQueryString = function(){
		var arr = [];
		for(var i=0;i<this.keys.length;i++){
			if(this.map[this.keys[i]]==null||this.map[this.keys[i]]==""){continue;}
			if(i!=0){
				arr.push("&");
			}
			arr.push(this.keys[i]+"="+this.map[this.keys[i]]);
		}
		return arr.join('');
	};

	DataCollection.prototype.parseXML = function(xmlDoc){
		var coll = xmlDoc.documentElement;
		if(!coll){
			return false;
		}
		var nodes = coll.childNodes;
		var len = nodes.length;
		for(var i=0;i<len;i++){
			var node = nodes[i];
			var Type = node.getAttribute("Type");
			var ID = node.getAttribute("ID");
			this.valuetype[ID] = Type;
			if(Type=="String"){
				var v = node.firstChild.nodeValue;
				if(v==Constant.Null){
					v = null;
				}
				this.map[ID] = v;
			}else if(Type=="StringArray"){
				this.map[ID] = eval("["+node.firstChild.nodeValue+"]");
			}else if(Type=="Map"){
				this.map[ID] = eval("("+node.firstChild.nodeValue+")");
			}else if(Type=="DataTable"||Type=="Schema"||Type=="SchemaSet"){
				this.parseDataTable(node,"DataTable");
			}else{
				this.map[ID] = node.getAttribute("Value");
			}
			this.keys.push(ID);
		}
		return true;
	};

	DataCollection.prototype.parseDataTable = function(node,strType){
		var cols = node.childNodes[0].childNodes[0].nodeValue;
		cols = "var _TMP1 = "+cols+"";
		eval(cols);
		cols = _TMP1;
		var values = node.childNodes[1].childNodes[0].nodeValue;
		values = "var _TMP2 = "+values+"";
		eval(values);
		values = _TMP2;
		var obj;
		obj = new DataTable();
		obj.init(cols,values);
		this.add(node.getAttribute("ID"),obj);
	};

	DataCollection.prototype.toXML = function(){
		var arr = [];
		arr.push("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		arr.push("<collection>");
		for(var i=0;i<this.keys.length;i++){
			var ID = this.keys[i];
			try{
				var v = this.map[ID];
				arr.push("<element ID=\""+ID+"\" Type=\""+this.valuetype[ID]+"\">");
				if(this.valuetype[ID]=="DataTable"){
					arr.push(v.toString());
				}else if(this.valuetype[ID]=="String"){
					if(v==null||typeof(v)=="undefined"){
						arr.push("<![CDATA["+Constant.Null+"]]>");
					}else{
						arr.push("<![CDATA["+v+"]]>");
					}
				}else if(this.valuetype[ID]=="Map"){
					if(v==null||typeof(v)=="undefined"){
						arr.push("<![CDATA["+Constant.Null+"]]>");
					}else{
						arr.push("<![CDATA["+JSON.toString(v)+"]]>");
					}
				}else{
					arr.push(v);
				}
				arr.push("</element>");
			}catch(ex){alert("DataCollection.toXML():"+ID+","+ex.message);}
		}
		arr.push("</collection>");
		return arr.join('');
	};

	DataCollection.prototype.add = function(ID,Value,Type){
		this.map[ID] = Value;
		this.keys.push(ID);
		if(Type){
			this.valuetype[ID] = Type;
		}else	if( Value && Value.getDataRow){//DataTable可能不是本页面中的
			this.valuetype[ID] = "DataTable";
		}else{
			this.valuetype[ID] = "String";
		}
	};

	DataCollection.prototype.addAll = function(dc){
		if(!dc){
			return;
		}
		if(!dc.valuetype){
			alert("DataCollection.addAll()要求参数必须是一个DataCollection!");
		}
		var size = dc.size();
		for(var i=0;i<size;i++){
			var k = dc.keys[i];
			var v = dc.map[k];
			var t = dc.valuetype[k];
			this.add(k,v,t);
		}
	};
}



function premRecal(riskcode, ele, appFactor, recordSel) {
	/* 复杂产品保费试算分支 by 宋再冉 */

	if(complicatedFlag == "Y") {
        if (riskcode == "224801001") {
			compRecalTemp(riskcode);
		} else {
			compRecal(riskcode, recordSel);
		}
		return;
	}
	/* ------------------------------ */ 

	if (ele.parentNode.className == "li_selected") {
		return;
	} 
	var appEele = document.getElementById(appFactor);
	var appValue = "";
	if (ele.getAttribute("name") == "inpRiskAppFactor_TextAge") {  
		if (ele.value == "") {
			return;
		}
		var _insuredAge = getInsuredAge(ele.value);
		if (_insuredAge < 0){
			return;
		}
		ele = appEele.getElementsByTagName("SPAN")[0];
		ele.setAttribute("name",_insuredAge + "Y");
		ele.innerHTML = _insuredAge + "周岁";
	}
	if (appEele.tagName == "UL") {
		changeAppFactorStyle(appEele.getElementsByTagName("LI"), ele);
		appValue = ele.getAttribute("name");
	} else {
		appValue = ele.value;
	}

	changeDutyAmnt(appValue, appFactor.substring(0, 21), riskcode);
	premDocal(riskcode);
	// 如果是计划则需要改变责任条款 add by guobin
	// 列表页改变计划后，保额联动 modify by cuishigang
	// 由于列表页与详细页的责任保额显示布局已经不同，所以增加此部分供列表页显示
	//只有列表页非推荐产品部分联动保额，推荐产品不随之联动
	if(appFactor.indexOf("_Plan") != -1)	{
		var b = jQuery("[id=divRiskAppDuty_"+riskcode+"]")[1];
		//var a = document.getElementById("divRiskAppDuty_"+riskcode).getElementsByTagName("div");
		jQuery("[id=divRiskAppDuty_"+riskcode+"]:last").each(function(){
			jQuery(this).find("div").each(function(){
				jQuery(this).children().each(function(){
					var spanId = jQuery(this).attr("id");
					if(jQuery(this).attr("class")=="CDutyCol2"){
						//var Ids = spanId.split("_");
						//var sId = Ids[Ids.length-1];
						if(spanId!=null && spanId.substring(spanId.length-appValue.length - 1,spanId.length) == "_" + appValue){
							jQuery(this).show();
						}else{
							jQuery(this).hide(); //by
						}
					}
				});
			});
		});
		// 详细页改变计划后，保额联动 modify by cuishigang
		var divRiskAppDuty = document.getElementById("divRiskAppDuty_"+riskcode).getElementsByTagName("td");
		for ( var i = 0; i < divRiskAppDuty.length; i++) {
			if(divRiskAppDuty[i].className == 'CDutyCol2' && divRiskAppDuty[i].getElementsByTagName("span").length>0){
				var CDutyCol2 = divRiskAppDuty[i].getElementsByTagName("span");
				for(var j = 0; j < CDutyCol2.length; j++){
					//转换判断方法为: 截取spanid最后appvalue.length长度的字符串与appValue比较看是否相等 
					var spanId = CDutyCol2[j].getAttribute("id");
					//alert(spanId+"--"+spanId.substring(spanId.length-appValue.length,spanId.length)+"--"+appValue)
					//var Ids = spanId.split("_");
					//var sId = Ids[Ids.length-1];
					if(spanId!=null&& spanId.substring(spanId.length-appValue.length - 1,spanId.length) == "_" + appValue ){
						CDutyCol2[j].style.display = "block";
					}  else {
						CDutyCol2[j].style.display = "none";
					}
				}
			}
		}
	}
}

/** 
 * @des: 产品详细页优化：复杂产品保费试算传参
 * @maker: Songzairan
 * @date: 2014.11.27
 */
function compRecal(riskcode, recordSel) {
	var param = {};
	var selectedLi = jQuery(".bz_time .li_selected");
	var selectedTd = jQuery("#gh_tables td.CDutyCol2").find("select, span");
	var jAutoSelDay = jQuery(".cp_descon #UlDayBelongs > ul > li > span:not(#LiDayItemAuto)");
	var jDaySel = jQuery("#LiDayItemAuto");
	var plantemp = "";
	/**/
	selectedLi.each(function(i, v) {
		var _key, _val;
		
		if(jQuery(v).children().attr("id") == "LiDayItemAuto") {
			var _nowNum = parseInt(jDaySel.text());
			var maxDayScope = jQuery(jAutoSelDay[jAutoSelDay.length - 2]).text().slice(0, -1).split("-");
			var selDayMax;
			
			if(maxDayScope.length == 1) { 
				selDayMax = maxDayScope[0]
			} else if (maxDayScope.length > 1) {
				selDayMax = maxDayScope[1]
			}
			
			jAutoSelDay.each(function(i, u) {
				if(i == jAutoSelDay.length - 1) return;
				
				var dayScope = jQuery(u).text().slice(0, -1).split("-");

				if((dayScope.length == 1 && _nowNum == dayScope[0]) || (dayScope.length > 1 && _nowNum >= dayScope[0] && _nowNum <= dayScope[1])) {
					_val = jQuery(u).attr("name");
				}	
			});
			
			if (_nowNum > selDayMax) {
				jDaySel.parent().removeClass("li_selected");
				jDaySel.text("自主选择");
				jAutoSelDay.eq(jAutoSelDay.length -1).parent().addClass("li_selected");
				_val = jAutoSelDay.eq(jAutoSelDay.length -1).attr("name");
			}
		} else {
			_val = jQuery(v).children().attr("name");
		}
		
		_key = jQuery(v).parent().attr("id");
		
		param[""+ _key +""] = _val;
		if (_key.indexOf("Plan") > 0) {
			plantemp = _val;
		}
	});
	/**/
	param["RiskCode"] = riskcode;
	param["complicatedFlag"] = complicatedFlag;
	var channelsn = "wj";
	var channel_sn = jQuery.cookie('channelSn');
	if(typeof(channel_sn)!="undefined" && channel_sn!=null && channel_sn!=""){
		channelsn = channel_sn;
	} 
	param["channelSn"] = channelsn;
	var duty = '';
	selectedTd.each(function(i, v) {
		var _key = v.id.slice(0, v.id.indexOf("_"));
		var _val;
		
		if(v.nodeName == "SELECT") {
			_val = jQuery(v).val();	
		} else if(v.nodeName == "SPAN") {
			_val = jQuery(v).text();	
		}
		if(_val.search(/不投保/) != -1) {
			_val = 0;
		} else if(_val.search(/万/) != -1) {
			_val = parseFloat(_val)*10000;
		} else if(_val.search(/元\/天/) != -1) {
			_val = parseFloat(_val);
			if (riskcode.indexOf("2100") >= 0) {
				_val = _val * 180;
			}
			
		} else if(_val.search(/-/) != -1) {
			return;
		} else if(_val.search(/是/) != -1) {
			_val = parseFloat(1);
		} else if(_val.search(/否/) != -1) {
			_val = parseFloat(0);
		}else if(_val.search(/豁免保费/) != -1) {//豁免保费昆仑健康树使用：_val值！=0，判断轻症责任是否选择。
			var temp = jQuery("#106501033001_01").text();
			_val = parseFloat(temp)*10000;
		}else if(isNaN(parseInt(_val.split("/")[0]))) {
			_val = 0;
		} else if(parseInt(_val.split("/")[0]) != -1) {
			var var1 = parseInt(_val.split("/")[1]);
			_val = parseFloat(_val.split("/")[0]);
			if(_val == 500 && var1 == 6) {
				_val = 500.0;
			}
		}
		
		duty +=  _key + '-' + _val +',';
	});
	param["duty"] = duty.substring(0,duty.length-1) ;

	jQuery.ajax({
		type: "POST",
		url: sinosoft.base + '/shop/filter!premDoCal.action',
		data: param,
		dataType: "json",
		success: function(data){
			if(data && data.status == '1'){
			    var prem = data.productPrem;
				var ratePrem = data.productRatePrem;
				// 现价
				if (prem != null && prem != "") {
					document.getElementById("productPrem_" + riskcode).innerHTML ="<em>￥</em>"+ prem;
				}
				// 原价 
				if (ratePrem != null && ratePrem != "") {
					document.getElementById("productRatePrem_" + riskcode).innerHTML = ratePrem;
				}
				// 积分
				var integral = data.productIntegral;
				if (integral != null && integral != "") {
					if('true'==data.pointDesFlag&&data.pointDes!=''){
						jQuery("#pointdes").html(data.pointDes);
					}else{
						var desc="<span id=\"pointtitle_"+riskcode+"\"></span>您将获得&nbsp;<span id=\"productIntegral_"+riskcode+"\">"+integral+"</span>&nbsp;个积分";
						//document.getElementById("productIntegral_" + riskcode).innerHTML = integral;
						jQuery("#pointdes").html(desc);
					}
				}
				//最多抵扣金额
				var IntePointAmountgral = data.IntePointAmountgral+"";
				if (IntePointAmountgral != null && IntePointAmountgral != "" && parseFloat(IntePointAmountgral)!=0) {
					if (document.getElementById("maxIntegralPrice_" + riskcode)&&document.getElementById("maxIntegralPrice_" + riskcode) != null) {
						if(data.loginFlag=='false'){
							document.getElementById("maxIntegralPrice_" + riskcode).innerHTML = "<i class=\"jf_icons\">&nbsp;</i><span>积分最多可抵<em>¥ "+IntePointAmountgral+"</em></span>";
						}else{
							document.getElementById("maxIntegralPrice_" + riskcode).innerHTML = "<i class=\"jf_icons\">&nbsp;</i><span>您已有积分 "+data.MemberAmountgral+" 可抵值<em>¥ "+IntePointAmountgral+"</em></span>";
						}
					}
				}
				var msg = data.msg;
				if (msg != null && msg != '') {
					alert(msg);
				}
			} else if (data && data.status == '2') {
				alert(data.msg);
			} 
			
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			//console.log(XMLHttpRequest.status);
		}
	});
}

/**
 * @des: 百年康惠保重大疾病保险 为责任上调使用，如果将来要统一，可以以此为基础修改
 * @maker: liyinfeng
 * @date: 2016.7.6
 */
function compRecalTemp(riskcode) {
	var param = {};
	var selectedLi = jQuery(".bz_time .li_selected");
	var duty1 = '';
	var duty2 = '';
	var planVal = '';
	/**/
	selectedLi.each(function(i, v) {
		var _key, _val;
		_val = jQuery(v).children().attr("name");
		_key = jQuery(v).parent().attr("id");

		param[""+ _key +""] = _val;
		// 计划关联责任
		if (_key.indexOf("Plan") > 0) {
			duty1 = '224801001001-'+_val;
			planVal = _val;
			jQuery("#duty01").html(parseFloat(_val)/10000+"万");
		}

		if (_key == 'duty' && _val == '0') {
			duty2 ='224801001002-'+_val;
			jQuery("#duty02").html("不投保");
		} else if(_key == 'duty' && _val != '0'){
			duty2 ='224801001002-'+ planVal;
			jQuery("#duty02").html("投保");
		}
	});
	
	var _duty02 = jQuery("#224801001002_02").val();
	if(_duty02 && typeof(_duty02) != 'undefined'){
		if( _duty02 =='投保' ){
			duty2 ='224801001002-'+ planVal;
		} else {
			duty2 ='224801001002-0';
		}
	}
	
	/**/
	param["RiskCode"] = riskcode;
	param["complicatedFlag"] = complicatedFlag;
	var channelsn = "wj";
	var channel_sn = jQuery.cookie('channelSn');
	if(typeof(channel_sn)!="undefined" && channel_sn!=null && channel_sn!=""){
		channelsn = channel_sn;
	}
	param["channelSn"] = channelsn;
	param["duty"] = duty1 + "," + duty2 ;

	jQuery.ajax({
		type: "POST",
		url: sinosoft.base + '/shop/filter!premDoCal.action',
		data: param,
		dataType: "json",
		success: function(data){
			if(data && data.status == '1'){
				var prem = data.productPrem;
				var ratePrem = data.productRatePrem;
				// 现价
				if (prem != null && prem != "") {
					document.getElementById("productPrem_" + riskcode).innerHTML ="<em>￥</em>"+ prem;
				}
				// 原价
				if (ratePrem != null && ratePrem != "") {
					document.getElementById("productRatePrem_" + riskcode).innerHTML = ratePrem;
				}
				// 积分
				var integral = data.productIntegral;
				if (integral != null && integral != "") {
					if('true'==data.pointDesFlag&&data.pointDes!=''){
						jQuery("#pointdes").html(data.pointDes);
					}else{
						var desc="<span id=\"pointtitle_"+riskcode+"\"></span>您将获得&nbsp;<span id=\"productIntegral_"+riskcode+"\">"+integral+"</span>&nbsp;个积分";
						//document.getElementById("productIntegral_" + riskcode).innerHTML = integral;
						jQuery("#pointdes").html(desc);
					}
				}
				//最多抵扣金额
				var IntePointAmountgral = data.IntePointAmountgral+"";
				if (IntePointAmountgral != null && IntePointAmountgral != "" && parseFloat(IntePointAmountgral)!=0) {
					if (document.getElementById("maxIntegralPrice_" + riskcode)&&document.getElementById("maxIntegralPrice_" + riskcode) != null) {
						if(data.loginFlag=='false'){
							document.getElementById("maxIntegralPrice_" + riskcode).innerHTML = "<i class=\"jf_icons\">&nbsp;</i><span>积分最多可抵<em>¥ "+IntePointAmountgral+"</em></span>";
						}else{
							document.getElementById("maxIntegralPrice_" + riskcode).innerHTML = "<i class=\"jf_icons\">&nbsp;</i><span>您已有积分 "+data.MemberAmountgral+" 可抵值<em>¥ "+IntePointAmountgral+"</em></span>";
						}
					}
				}
				var msg = data.msg;
				if (msg != null && msg != '') {
					alert(msg);
				}
			} else if (data && data.status == '2') {
				alert(data.msg);
			}

		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			//console.log(XMLHttpRequest.status);
		}
	});
}

function getDay(day) {
	if(day.length<=1){
		return 0;
	}else{
		return day.substring(0,day.length-1);
	}
}
function getMaxDay(maxDay) {
	if(maxDay.indexOf("-") != -1){
		var s=maxDay.split("-");
		maxDay=s[1];
	}
	if(maxDay.substring(maxDay.length-1,maxDay.length)=="Y"){
		var maxDayInt=parseInt(maxDay.replace("Y",""))*365;
		maxDay=maxDayInt+"D";
	}
	return maxDay.substring(0,maxDay.length-1);
}
function premDocal(riskcode) {
	
	
	var rootEle = document.getElementById("divRiskAppFactor_" + riskcode);	
	var day=0;
	var maxDay=0;
	jQuery("#DayE").html('');
	var param = "prem_callback=?";
	if (rootEle != null) {
		var dc = new DataCollection();
		var ulNodeList = rootEle.getElementsByTagName("UL");
		for ( var i = 0; i < ulNodeList.length; i++) {
			for ( var j = 0; j < ulNodeList[i].childNodes.length; j++) {
				if (ulNodeList[i].childNodes[j].className == "li_selected") {
					if(ulNodeList[i].childNodes[j].firstChild.getAttribute("id")=="LiDayItemAuto"){
						 day=parseInt(getDay(jQuery("#UlDayBelongs").find(".li_selected").children().html()));
						 maxDay=parseInt(getMaxDay(jQuery("#UlDayBelongs").find(".li_selected").prev().children().attr("name")));
						if(day >= maxDay){
							jQuery("#UlDayBelongs").find(".li_selected").prev().children().click();
							
							jQuery("#LiDayItemAuto").html('自主选择');
							jQuery("#DayE").html('您选择的天数已超出范围，已帮您选择最大期限');
						}else{
							dc.add(ulNodeList[i].id, day+"@");
							jQuery("#DayE").html('');
							param += "&" + ulNodeList[i].id + "=" + (day+"@");
						}
						
					}else{
						dc.add(ulNodeList[i].id, ulNodeList[i].childNodes[j].firstChild.getAttribute("name"));
						param += "&" + ulNodeList[i].id + "=" + encodeURI(ulNodeList[i].childNodes[j].firstChild.getAttribute("name"));
					}
					continue;
				}
			}
		}
		var selNodeList = rootEle.getElementsByTagName("SELECT");
		for ( var i = 0; i < selNodeList.length; i++) {
			dc.add(selNodeList[i].id, selNodeList[i].value);
			param += "&" + selNodeList[i].id + "=" + encodeURI(selNodeList[i].value);
		}
		
		// 积分商城详细页面区分产品详细页面标记
		if(jQuery("#pointproducttype").length!=0){
			param += "&pointproducttype=" + jQuery("#pointproducttype").val() ;
		}
		if(!isNaN(day) && day <= maxDay ){
			if (dc.size() > 0) {
				dc.add("RiskCode", riskcode);
				param += "&RiskCode=" + riskcode;
				param += "&complicatedFlag=" + complicatedFlag;
				
				var channelsn = "wj";
				var channel_sn = jQuery.cookie('channelSn');
				if(typeof(channel_sn)!="undefined" && channel_sn!=null && channel_sn!=""){
					channelsn = channel_sn;
				} 
				param += "&channelSn=" + channelsn;
				jQuery.getJSON(sinosoft.base + '/shop/filter!premDoCal.action?' + param ,
					function(data) {
						 if(data && data.status == '1'){
							    var prem = data.productPrem;
								var ratePrem = data.productRatePrem;
								// 现价
								if (prem != null && prem != "") {
									document.getElementById("productPrem_" + riskcode).innerHTML ="<em>￥</em>"+ prem;
								}
								// 原价 
								if (ratePrem != null && ratePrem != "") {
									document.getElementById("productRatePrem_" + riskcode).innerHTML = ratePrem;
								}
								// 积分
								var integral = data.productIntegral;
								if (integral != null && integral != "") {
									if('true'==data.pointDesFlag&&data.pointDes!=''){
										jQuery("#pointdes").html(data.pointDes);
									}else{
										var desc="<span id=\"pointtitle_"+riskcode+"\"></span>您将获得&nbsp;<span id=\"productIntegral_"+riskcode+"\">"+integral+"</span>&nbsp;个积分";
										//document.getElementById("productIntegral_" + riskcode).innerHTML = integral;
										jQuery("#pointdes").html(desc);
									}
								}
								//最多抵扣金额
								var IntePointAmountgral = data.IntePointAmountgral+"";
								if (IntePointAmountgral != null && IntePointAmountgral != "" && parseFloat(IntePointAmountgral)!=0) {
									if(document.getElementById("maxIntegralPrice_" + riskcode)&&document.getElementById("maxIntegralPrice_" + riskcode)!=null){
										if(data.loginFlag=='false'){
											document.getElementById("maxIntegralPrice_" + riskcode).innerHTML = "<i class=\"jf_icons\">&nbsp;</i><span>积分最多可抵<em>¥ "+IntePointAmountgral+"</em></span>";
										}else{
											document.getElementById("maxIntegralPrice_" + riskcode).innerHTML = "<i class=\"jf_icons\">&nbsp;</i><span>您有积分<em>"+data.MemberAmountgral+"</em> 本次可抵<em>¥ "+IntePointAmountgral+"</em></span>";
										}
									}
								}
						 } // 积分商城详细页面
							else if (data && data.status == '3') {
								  var prem = data.productPrem;
								if (prem != null && prem != "") {
									document.getElementById("productPrem_" + riskcode).innerHTML =  prem + "积分";
									document.getElementById("productLashNum_" + riskcode).innerHTML =  data.productLashNum + "个";
								}
							} 
				});
				
			}
		}
	}
}

function changeAppFactorStyle(DocElements, curElement) {
	for ( var i = 0; i < DocElements.length; i++) {
		DocElements[i].className = "";
	}
	curElement.parentNode.className = "li_selected";
}

function changeDutyAmnt(appFactorValue, appFactorCode, cRiskCode) {
	var rootNodeEle = document.getElementById("divRiskAppDuty_" + cRiskCode);
	if (rootNodeEle) {
		var dutyEles = rootNodeEle.getElementsByTagName("span");
		for ( var i = 1; i < dutyEles.length; i++) {
			if (dutyEles[i].id.substring(21, 42) == appFactorCode) {
				dutyEles[i].style.display = "none";
				if (dutyEles[i].id.substring(42) == appFactorValue) {
					dutyEles[i].style.display = "";
				}
			}
		}
	}
}

function getInsuredAge(_strBrithday){
	var age;
	var brith;
	if(_strBrithday.match(/^\d{4}[\-\/\s+]\d{1,2}[\-\/\s+]\d{1,2}$/)){
		brith= new Date(_strBrithday.replace(/[\-\/\s+]/g,'/'));
	}else if(_strBrithday.match(/^\d{8}$/)){  
		brith= new Date(_strBrithday.substring(0,4)+'/'+_strBrithday.substring(4,6)+'/'+_strBrithday.substring(6));
	}else{
		alert('生日格式错误！');
		return -1;
	}
	var aDate=new Date();
	var thisYear=aDate.getFullYear();
	var thisMonth=aDate.getMonth()+1;
	var thisDay=aDate.getDate();
	
	brithy=brith.getFullYear();
	brithm=brith.getMonth();
	brithd=brith.getDate();
	if(thisYear-brithy<0){
		age= -1;
	}else{
		if(thisMonth-brithm<0) {
			age = thisYear-brithy-1;
		} else {
			if(thisDay-brithd>=0) {
				age = thisYear-brithy;
			} else {
				age = thisYear-brithy-1;
			}
		}
	}
	return age;
}