function SearchOldAddress(){	//「住所検索」ボタンが押された時に呼ばれる関数
  var zipcode=document.getElementById("oldZip").value;
  if( ! CheckZipcode(zipcode)){	//郵便番号のチェック
    alert("[ "+zipcode+" ]は正しくない郵便番号です");
    return ;
  }
  Ctrl_cursor("wait");	//マウスカーソルを待機状態にする
  RemoveScript("https://geoapi.heartrails.com/api/json?method=searchByPostal&postal=");
  var target=document.createElement("script");
  target.src = "https://geoapi.heartrails.com/api/json?method=searchByPostal&postal="+encodeURIComponent(zipcode)+"&jsonp=GetOldAddress";	//HeartRails Geo のサイトから住所情報を取得
  document.body.appendChild(target);
}
function SearchNewAddress(){	//「住所検索」ボタンが押された時に呼ばれる関数
  var zipcode=document.getElementById("newZip").value;
  if( ! CheckZipcode(zipcode)){	//郵便番号のチェック
    alert("[ "+zipcode+" ]は正しくない郵便番号です");
    return ;
  }
  Ctrl_cursor("wait");	//マウスカーソルを待機状態にする
  RemoveScript("https://geoapi.heartrails.com/api/json?method=searchByPostal&postal=");
  var target=document.createElement("script");
  target.src = "https://geoapi.heartrails.com/api/json?method=searchByPostal&postal="+encodeURIComponent(zipcode)+"&jsonp=GetNewAddress";	//HeartRails Geo のサイトから住所情報を取得
  document.body.appendChild(target);
}
function CheckZipcode(zipcode){	//郵便番号のチェック
  if(!zipcode) return false;	//郵便番号が入力されていない
  if(!zipcode.match(/^\d{7}$/)){	//入力された郵便番号が７桁であるか
    return false;
  }
  return true;
}
function GetOldAddress(zip){	//コールバック関数(住所情報の取得)
  Ctrl_cursor("default");	//マウスカーソルを元に戻す
  if(!zip.response.error){
    document.getElementById("oldPrefectureId").value=zip.response.location[0].prefecture;
    var options = document.getElementById('oldPrefectureId').options;
    if(zip.response.location[0].prefecture === '北海道') {
        options[0].selected = true;
    } else {
       for(var i = 0; i < options.length; i++){
           if(options[i].text === zip.response.location[0].prefecture.slice(0, -1)){
               options[i].selected = true;
               break;
           };
       };
    }
    document.getElementById("oldAddress").value=zip.response.location[0].city+zip.response.location[0].town;
    document.getElementById("oldX").value=zip.response.location[0].x;
    document.getElementById("oldY").value=zip.response.location[0].y;
  }else{
    alert(zip.response.error);
  }
}
function GetNewAddress(zip){	//コールバック関数(住所情報の取得)
  Ctrl_cursor("default");	//マウスカーソルを元に戻す
  if(!zip.response.error){
    document.getElementById("newPrefectureId").value=zip.response.location[0].prefecture;
    var options = document.getElementById('newPrefectureId').options;
    if(zip.response.location[0].prefecture === '北海道') {
        options[0].selected = true;
    } else {
       for(var i = 0; i < options.length; i++){
           if(options[i].text === zip.response.location[0].prefecture.slice(0, -1)){
               options[i].selected = true;
               break;
           };
       };
    }
    document.getElementById("newAddress").value=zip.response.location[0].city+zip.response.location[0].town;
    document.getElementById("newX").value=zip.response.location[0].x;
    document.getElementById("newY").value=zip.response.location[0].y;
  }else{
    alert(zip.response.error);
  }
}
function Ctrl_cursor(ctrl){	//マウスカーソルの制御
  document.body.style.cursor = ctrl;
  var tag=Array('input','a','button','select');
  for(var j=0;j<tag.length;j++){
    for (var i = 0; i < document.getElementsByTagName(tag[j]).length; i++) {
      document.getElementsByTagName(tag[j])[i].style.cursor = ctrl;
    }
  }
}
function RemoveScript(url){
  for(var i=document.scripts.length-1;i>=0;i--){
    var p=document.getElementsByTagName('script')[i];
    if(p.getAttribute('src')!=null&&p.getAttribute('src').indexOf(url)==0){
      p.parentNode.removeChild(p);
    }
  }
}