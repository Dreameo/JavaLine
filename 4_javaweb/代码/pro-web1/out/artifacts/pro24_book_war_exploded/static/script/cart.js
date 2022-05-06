function editCart(cartItemId, buyCount) {
    window.location.href = "cart.do?operation=editCart&cartItemId=" + cartItemId +"&buyCount=" + buyCount;
}