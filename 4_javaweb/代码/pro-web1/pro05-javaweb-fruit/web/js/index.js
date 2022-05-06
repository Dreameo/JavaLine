function delFruit(fid) {
    if(confirm('确定删除？')) {
        window.location.href='del.do?fid='+fid;
    }
}