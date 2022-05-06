function delFruit(fid) {
    if(confirm('确定删除？')) {
        window.location.href='del.do?fid='+fid;
    }
}

function page(pageNo) {
    // alert(pageNo);
    window.location.href='index?pageNo=' + pageNo;
}