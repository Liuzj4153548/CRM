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
    <title>图表演示</title>
    <script type="text/javascript">
        $(function () {
            //发送查询请求
            $.ajax({
                url:'workbench/chart/transaction/queryCountOfTranGroupByStage.do',
                type:'post',
                dataType:'json',
                success:function (data) {
                    //调用工具函数，显示漏斗图
                    //当容器加载完成之后，对容器调用工具函数
                    //基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('main'));

                    //指定图表的配置项和数据
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
                                data: data
                            }
                        ]
                    };
                    //使用刚指定的配置项和数据显示图表
                    myChart.setOption(option);
                }
            });
        });
    </script>
</head>
<body>
<%--准备容器--%>
<div id="main" style="width: 600px;height: 400px;"></div>
</body>
</html>
