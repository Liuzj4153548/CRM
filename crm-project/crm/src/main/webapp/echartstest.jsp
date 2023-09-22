<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <%--1、引入jQuery--%>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <%--引入echarts插件--%>
    <script type="text/javascript" src="jquery/echarts/echarts.min.js"></script>
    <title>演示echarts插件</title>
    <script type="text/javascript">
        $(function () {
            //当容器加载完成之后，对容器调用工具函数
            //基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            //指定图表的配置项和数据
            /*var option = {
                title: {
                    text:'ECharts 入门实例',
                    subtext: '测试副标题',
                    textStyle: {
                        fontStyle:'italic'
                    }
                },
                tooltip:{
                    textStyle: {
                        color:'blue'
                    }
                },//提示框
                legend: {//图例
                    data:['销量']
                },
                xAxis: {//横轴数据项
                    data:["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
                },
                yAxis: {//数据量轴

                },
                series: [{//系列
                    name: '销量',//系列名称和图例中保持一致
                    type: 'line',//系列类型，柱状图line折线图
                    data: [5,20,36,10,10,20]//系列数据
                }]
            };
            //使用刚指定的配置项和数据显示图表
            myChart.setOption(option);
             */
            var option = {
                title: {
                    text: '交易统计图表',
                    subtext: '交易表中各个阶段的数量'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c}'
                },
                toolbox: {
                    feature: {
                        dataView: { readOnly: false },
                        restore: {},
                        saveAsImage: {}
                    }
                },
                series: [
                    {
                        name: '数据量',
                        type: 'funnel',
                        left: '10%',
                        top: 60,
                        bottom: 60,
                        width: '80%',
                        min: 0,
                        max: 100,
                        minSize: '0%',
                        maxSize: '100%',
                        sort: 'descending',
                        gap: 2,
                        label: {
                            show: true,
                            position: 'inside'
                        },
                        labelLine: {
                            length: 10,
                            lineStyle: {
                                width: 1,
                                type: 'solid'
                            }
                        },
                        itemStyle: {
                            borderColor: '#fff',
                            borderWidth: 1
                        },
                        emphasis: {
                            label: {
                                fontSize: 20
                            }
                        },
                        data: [
                            { value: 60, name: '访问' },
                            { value: 40, name: '咨询' },
                            { value: 20, name: '订单' },
                            { value: 80, name: '点击' },
                            { value: 100, name: '展示' }
                        ]
                    }
                ]
            };
            //使用刚指定的配置项和数据显示图表
            myChart.setOption(option);
        });
    </script>
</head>
<body>
<%--准备容器--%>
<div id="main" style="width: 600px;height: 400px;"></div>
</body>
</html>
