resolver 10.11.20.188;
location ~* /zhuanti/(.*)\.kuaizhan.com/(.*) {
                 proxy_set_header Host $1.kuaizhan.com;
                 proxy_pass  http://$1.kuaizhan.com/$2;
}
location ~* /pinge/(.*) {
                proxy_set_header Host pinge.focus.cn;
                proxy_pass http://pinge.focus.cn/w/$1;
}
location ~* /fangdai/(.*) {
                proxy_set_header Host fangdai.focus.cn;
                proxy_pass http://fangdai.focus.cn/wap/$1;
}
location /app/ {
                client_max_body_size    100m;
                client_body_buffer_size 1024k;
                proxy_connect_timeout   30;
                proxy_pass    http://10236/static/appwap.html;
                proxy_redirect off;
                proxy_set_header Host $host;
}
location ~ "/bbs" {
                 proxy_set_header Host $host;
                 proxy_pass http://wap.interface.focus.cn;
}
location / {
                client_max_body_size    100m;
                client_body_buffer_size 1024k;
                proxy_connect_timeout   30;
                proxy_pass    http://10236;
                proxy_redirect off;
                proxy_set_header Host $host;
}
