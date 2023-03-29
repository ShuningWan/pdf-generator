<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <style type="text/css">

        @page {
            margin: 86.51px 34px 53.84px 34px;
        }

        body {
            /* 这两个在技术上是一样的, 为了兼容了浏览器两个都加上 */
            overflow-wrap: break-word;
            word-wrap: break-word;
            -ms-word-break: break-all;
            /* 这个的使用在web-kit中有些危险，他可能会阶段所有东西 */
            word-break: break-all;
            /* Instead use this non-standard one: */
            word-break: break-word;
            /* 如果浏览器支持的话增加一个连接符(Blink不支持) */
            -ms-hyphens: auto;
            -moz-hyphens: auto;
            -webkit-hyphens: auto;
            hyphens: auto;
        }

        .page_break {
            page-break-after: always;
        }
    </style>
</head>
<body>
<div>
    <span style="font-size: 32px;font-weight: bold">测试图片: </span>
    <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABC8AAAAJCAYAAAASCwYFAAAACXBIWXMAABcSAAAXEgFnn9JSAAACoUlEQVR4Xu3YTWwUdRjH8e8zLWlWICWkFxMsUk94wkBi5EI8kfQlG7Z0UZDiCRMOhiBKqZVSZX0hkJgYDuWilMSWLcV0d230ZLiZcKAXDAeDKS9qlGiIZrOU7jwciGTy966d4fc5/r6zpznM5rGpmaoj2ePMx1F0dHeh55vkXO4aaG88uH8Y84PAqmQTERERkf/MHwYn6w/8szd+qdXDCHBhpvayE38E9mLYRESeKGYl0/Eic35yfOSVQt+kmT1+t+V1A7n7UeNNh3eAtYnnRUREROT/86uZldpWtp0t/jC9GEaAyZnqDoMSsDFsIiJPBB0vMuV3sA+iZn28WCw+/vCNb96/Inf35/0474I9nfyBiIiIiCwbC+Y+1nYrN1FkuhnGcrncErfmBnGOA51hFxHJNB0vMuFvh9NPtcan8/n8X/+Mo4xGXZ1X9xo+CmxIPC8iIiIiy9d1g9HXblanDf71P31ubq7tXmPpgLkNAx1hFxHJJB0vUm0RfHzRlk4MFgq/JcP59X397rwPPJ/cRURERCQdDJvHGNm7UPk6bACzs7Or60vRWwaHgNVhFxHJFB0vUsmByabbe3t29t5IholnerZj0QlgS3IXERERkXRy53tzhgZvVy+HDeDLSqUjWrIRnAPAirCLiGSCjhep820URUPFHT3zyfH8+vxWJ/4QZ1tyFxEREZHMqMUw9PrN6rUwAEx9Nfecxc2PHXaGTUQk9XS8SI0rhh3Z1d/7XXL8/NnuTS1xSwnoTu4iIiIikklN3M+5c2zf7dqdMAKUL1Vfip1TwNawiYik1qPjReXHcJdl40/HPnm1v+9icix3DbQ3mo233SkaHiWbiIiIiGSbQx2LPr2x8MIXY4zFYQeYulQreOzDZr4mbCIiaePGmYdsyOHyKVTjpQAAAABJRU5ErkJggg==" width="100%" height="9" />
</div>
<div>
    <span style="font-size: 32px;font-weight: bold">测试数据: </span>
    <p>中文: ${chinese!'-'}</p>
    <p>英文: ${english!'-'}</p>
    <p>符号: ${symbol!'-'}</p>
    <p>数字: ${number!'-'}</p>
</div>
</body>
</html>
