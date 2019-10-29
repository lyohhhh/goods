let meView = document.querySelector(".me-view");

/**
 *  资料卡
 */
$(".me-tips ul li").click(function() {
    meView.style.transform = `translateX(-${($(this).index())*360}px)`;
    $(this).addClass('show');
    $(this).siblings().removeClass("show");
});

let leftBtn = document.querySelector(".leftbtn");
let rightBtn = document.querySelector(".rightbtn");
let allLi = document.querySelectorAll(".slide-content a li");
let tips = document.querySelectorAll(".tips ul li");
let t, now = 0,
    old = 0;

/**
 * 指示灯移入移除事件
 */
for (var i = 0; i < tips.length; i++) {
    tips[i].old = i;
    tips[i].onmouseenter = function() {
        old = this.old;
        clearInterval(t);
        if (old == now) {
            return;
        } else {
            allLi[old].className = "active";
            allLi[now].className = "";
            tips[old].className = "tipsed";
            tips[now].className = "";
            now = old;
        }

    }
    tips[i].onmouseout = function() {
        timer();
    }
}

/**
 * 按钮事件
 */
rightBtn.addEventListener("click", () => {
    change(true);
    clearInterval(t);
    timer();
});
leftBtn.addEventListener("click", () => {
    change(false);
    clearInterval(t);
    timer();
});


/**
 *  幻灯片变化
 */
function change(change) {
    if (change) {
        old++
        if (old >= allLi.length) old = 0;
    } else {
        old--;
        if (old < 0) old = allLi.length - 1;
    }
    allLi[old].className = "active";
    allLi[now].className = "";
    tips[old].className = "tipsed";
    tips[now].className = "";
    now = old;
}



/**
 *  banner区域幻灯片
 */

function timer() {
    t = setInterval(function() {
        change(true);
    }, 2000);
}
timer();


let studyLeftBtn = document.querySelector(".studyleftbtn");
let studyRightBtn = document.querySelector(".studyrightbtn");
let view = document.querySelector(".view");
let allNum = document.querySelectorAll(".study .item").length - 3;
let num = 0;

/**
 *  个人资料幻灯片
 */
studyRightBtn.addEventListener("click", () => {
    num < 0 ? num = 1 : num++;
    if (num > allNum) {
        num = allNum;
        return;
    }
    view.style.transform = `translateX(-${num * 380}px)`;
});
studyLeftBtn.addEventListener("click", () => {
    num--;
    view.style.transform = `translateX(-${num * 380}px)`;
});


let introduceItem = document.querySelectorAll('.introduce-item');
let bH = window.screen.availHeight;
let rankingH = $('.ranking').offset().top;
let ranking = document.querySelector(".ranking");

const introduceArr = [];
$('.introduce-item').each(function() {
    introduceArr.push($(this).offset().top);
});


/**
 *  博客item动态显示
 */
function itemChange() {
    const blogsArr = [];
    $('.blogs-item').each(function() {
        blogsArr.push($(this).offset().top);
    });

    let blogItem = document.querySelectorAll('.blogs-item');
    let wH = document.documentElement.scrollTop || document.body.scrollTop;
    wH = wH + bH - 100;
    blogsArr.forEach(function(ele, i) {
        if (wH > blogsArr[i]) {
            blogItem[i].style.transform = 'translateY(-49px)';
            blogItem[i].style.opacity = '1';
        } else {
            blogItem[i].style.transform = 'translateY(-0px)';
            blogItem[i].style.opacity = '0';
        }
    });

    introduceArr.forEach(function(ele, i) {
        if (wH > introduceArr[i]) {
            introduceItem[i].style.transform = 'translateY(-49px)';
            introduceItem[i].style.opacity = '1';
        } else {
            introduceItem[i].style.transform = 'translateY(-0px)';
            introduceItem[i].style.opacity = '0';
        }
        let last = introduceArr.length - 1;
        if (wH > rankingH + 666) {
            ranking.className = "ranking introduce-item fixed";
        } else {
            ranking.className = "ranking introduce-item";
        }
    });
}

let chunk = []


/**
 *  API接口
 *  使用Ajax 访问
 */
function getSmall(page, callback) {
    let smalluri = 'https://www.apiopen.top/satinApi';
    $.ajax({
        url: smalluri,
        type: "GET",
        async: true,
        data: {
            type: 1,
            page: page
        },
        success: function(data) {
            chunk = data;
            callback(chunk);
            return false;
        }
    });
}



/** 创建博客板块函数  **/
function createElement(num) {
    let nameLenght;
    let releaseTime;
    let username;
    for (var i = 0; i < num; i++) {
        if (chunk.data[i].theme_name == null || chunk.data[i].theme_name == "") {
            chunk.data[i].theme_name = "无";
        }
        releaseTime = chunk.data[i].passtime;
        username = chunk.data[i].name;
        if (username.length > 6) {
            nameLenght = username.length - 6;
            username = username.substring(0, username.length - nameLenght);
        }
        releaseTime = releaseTime.substring(0, releaseTime.length - 9);
        let oDiv = `  <div class="blogs blogs-item">
                    <div class="img">
                        <img  alt="title" src="${chunk.data[i].image0}">
                    </div>
                    <div class="blogcontent">
                        <a href="">
                            <h4 id="content-title">${chunk.data[i].text}</h4>
                        </a>
                        <div class="showblog" id="content-val">
                            一个新面孔的惊人的游，食品，工艺品和其他许多 一个新面孔的惊人的游，食品，工艺品和其他许多 一个新面孔的惊人的游，食品，工艺品和其他许多 一个新面孔的惊人的游，食品，工艺品和其他许多 一个新面孔的惊人的游，食品，工艺品和其他许多 一个新面孔的惊人的游，食品，工艺品和其他许多dsadsadsa。
                        </div>
                    </div>
                    <div class="blogsfoot">
                        <ul>
                            <li>
                                <div class="icon">
                                    <img src="../images/indeximg/icon.jpg" alt="">
                                </div>
                                <a href="" id="username">${username}</a>
                            </li>
                            <li>
                                <i class="glyphicon glyphicon-th-list"></i>
                                <a href="" id="classification">${chunk.data[i].theme_name}</a>
                            </li>
                            <li>
                                <i class="glyphicon glyphicon-time"></i>
                                <span id="content-time">${releaseTime}</span>
                            </li>
                            <li>
                                <i class="glyphicon glyphicon-eye-open"></i>
                                <span id="frequency">${chunk.data[i].ding}次</span>
                            </li>
                            <li>
                                <i class="glyphicon glyphicon-heart"></i>
                                <span id="like">${chunk.data[i].love}人</span>
                            </li>
                        </ul>
                    </div>
                </div>`;
        $('.allblogs').append(oDiv);
    }
    $(".loading").css("top", $(".content").height() + "px");
    return false;
}


/**
 *  每次加载页面创建20个
 */
getSmall(1, function() {
    createElement(20);
    itemChange();
});


/** 
 * 返回顶部
 */
$(".backtop").click(function() {
    window.scrollTo(window.scrollLeft, 0);
});

/**
 * 监听滚动事件
 */
let page = 0;
addEvent(window, 'scroll', function() {
    let h = document.documentElement.scrollTop || document.body.scrollTop;
    let oH = document.documentElement.scrollHeight || document.body.scrollHeight;
    if (oH - h < 1000) {
        page++;
        getSmall(page);
        createElement(20);
        return false;
    }
});
addEvent(window, 'scroll', function() {
    itemChange();
    return false;
});
addEvent(window, 'onload', function() {
    itemChange();
    return false;
});

function getUser() {
    $.ajax({
        url: '/user',
        type: 'GET',
        async: true,
        success: function(data) {
            console.log(data[0]);
        }
    })
}