function delFruit(fid) {
    if(confirm('确定删除？')) {
        window.location.href='fruit.do?fid='+fid +'&operation=del';
    }
}

function page(pageNo) {
    // alert(pageNo);
    window.location.href='fruit.do?pageNo=' + pageNo;
}
