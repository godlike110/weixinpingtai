server  
{
    listen       80;     
    server_name  3g.focus.cn;
    rewrite ^/(.*) http://m.focus.cn/ last;
    access_log  off;
}