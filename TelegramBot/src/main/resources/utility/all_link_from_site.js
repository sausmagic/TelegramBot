
//ottenere tutte le url in una pagina web
var imageUrls = Array.prototype.map.call(document.images, function (i) {
    return i.src;
});