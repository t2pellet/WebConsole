/**
 WebConsole Language Manager for WebConsole
 Used to save your preferred language into your browser
 https://github.com/mesacarlos
 2019-2020 Carlos Mesa under MIT License.
*/
function setLanguage(locale){
	//Save to persistence
	persistenceManager.setLanguage(locale);
	//Set locale phrases
	switch(locale){
		case "en_US":
			lang = {
				"lang_dropdown": "Language",
				"passwordModalLongTitle": "Password required",
				"passwordModalLabel": "Password:",
				"passwordModalRememberLabel": "Remember password",
				"passwordModalCloseButton": "Close",
				"passwordSendButton": "Login",
				"disconnectionModalLongTitle": "Disconnected",
				"disconnectionModalDescription": "Connection was lost with the server. This can be caused by:",
				"disconnectionModalsub1": "Server was closed intentionally.",
				"disconnectionModalsub2": "Port is not opened on your host. In this case, troubleshoot using a port checker and recheck your firewall or router.",
				"disconnectionModalCloseButton": "Close",
				"disconnectionModalWelcomeScreenButton": "Back to welcome page",
				"settingsLink": "Settings",
				"settingsModalLongTitle": "WebConsole Settings",
				"showDateSettingsSwitchLabel": "Show time on each console line",
				"readLogFileSwitchLabel": "Retrieve full log file from server after login",
				"settingsModalCloseButton": "Done",
				"players_online": "Players Online",
				"cpu_title": "CPU",
				"ram_title": "RAM usage",
				"user_title": "Logged as",
				"deleteServerButton": "Delete server",
				"sendCommandButton": "Send"
			}
			break;
		case "es_ES":
			lang = {
				"lang_dropdown": "Idioma",
				"passwordModalLongTitle": "Se necesita contraseña",
				"passwordModalLabel": "Contraseña:",
				"passwordModalRememberLabel": "Recordar contraseña",
				"passwordModalCloseButton": "Cerrar",
				"passwordSendButton": "Iniciar sesión",
				"disconnectionModalLongTitle": "Desconectado",
				"disconnectionModalDescription": "Se perdió la conexión con el servidor. Esto puede deberse a:",
				"disconnectionModalsub1": "El servidor se cerró intencionadamente.",
				"disconnectionModalsub2": "El puerto no está abierto en el host. Utiliza un port checker para verificar que el puerto está abierto y comprueba tu firewall o router.",
				"disconnectionModalCloseButton": "Cerrar",
				"disconnectionModalWelcomeScreenButton": "Volver a pagina de inicio",
				"settingsLink": "Configuración",
				"settingsModalLongTitle": "Configuración de WebConsole",
				"showDateSettingsSwitchLabel": "Mostrar hora en cada linea de consola",
				"readLogFileSwitchLabel": "Leer log completo al iniciar sesión",
				"settingsModalCloseButton": "Hecho",
				"players_online": "Jugadores en línea",
				"cpu_title": "CPU",
				"ram_title": "RAM en uso",
				"user_title": "Iniciado sesion como",
				"deleteServerButton": "Borrar servidor",
				"sendCommandButton": "Enviar"
			}
			break;
		case "ru_RU": //Credit to Stashenko
			lang = {
				"lang_dropdown": "Язык",
				"passwordModalLongTitle": "Требуется пароль",
				"passwordModalLabel": "Пароль:",
				"passwordModalRememberLabel": "Запомнить пароль",
				"passwordModalCloseButton": "Закрыть",
				"passwordSendButton": "Войти",
				"disconnectionModalLongTitle": "Отключение!",
				"disconnectionModalDescription": "Было потеряно соединение с сервером. Это может быть вызвано:",
				"disconnectionModalsub1": "Сервер был закрыт намеренно.",
				"disconnectionModalsub2": "Порт не открыт на вашем хосте. В этом случае устраните неполадки с помощью средства проверки портов и еще раз проверьте свой брандмауэр или маршрутизатор.",
				"disconnectionModalCloseButton": "Закрыть",
				"disconnectionModalWelcomeScreenButton": "Вернуться на страницу приветствия",
				"settingsLink": "настройки",
				"settingsModalLongTitle": "настройки WebConsole",
				"showDateSettingsSwitchLabel": "Показать время в каждой строке консоли",
				"readLogFileSwitchLabel": "Получить полный файл журнала с сервера после входа в систему",
				"settingsModalCloseButton": "Выполнено",
				"players_online": "Игроки",
				"cpu_title": "CPU",
				"ram_title": "RAM",
				"user_title": "Вы вошли как",
				"deleteServerButton": "Удалить сервер",
				"sendCommandButton": "Отправить"
			}
			break;
		case "pt_BR": //Credit to AlexandreMuassab
			lang = {
				"lang_dropdown": "Idioma",
				"passwordModalLongTitle": "Necessário uma senha",
				"passwordModalLabel": "Senha:",
				"passwordModalRememberLabel": "Deseja salvar esta senha ?",
				"passwordModalCloseButton": "Fechar",
				"passwordSendButton": "Logar",
				"disconnectionModalLongTitle": "desconectado",
				"disconnectionModalDescription": "A conexão com o servidor foi perdida. Isso pode ser causado por:",
				"disconnectionModalsub1": "O servidor foi fechado intencionalmente.",
				"disconnectionModalsub2": "A porta não está aberta em seu host. Se for esse o caso, solucione o problema com um testador de porta e verifique seu firewall ou router novamente.",
				"disconnectionModalCloseButton": "Fechar",
				"disconnectionModalWelcomeScreenButton": "Voltar à página de boas-vindas",
				"settingsLink": "Configurações",
				"settingsModalLongTitle": "Configurações do WebConsole",
				"showDateSettingsSwitchLabel": "Mostrar hora em cada linha do console",
				"readLogFileSwitchLabel": "Recuperar arquivo de log completo do servidor após o login",
				"settingsModalCloseButton": "Feito",
				"players_online": "Jogadores online",
				"cpu_title": "Consumo de CPU",
				"ram_title": "Consumo de RAM",
				"user_title": "Logado como",
				"deleteServerButton": "Remover este servidor",
				"sendCommandButton": "Enviar"
			}
			break;
		case "zh_CN": //Credit to Neubulae
			lang = {
				"lang_dropdown": "语言",
				"passwordModalLongTitle": "需要密码",
				"passwordModalLabel": "密码:",
				"passwordModalRememberLabel": "记住密码",
				"passwordModalCloseButton": "关闭",
				"passwordSendButton": "登录",
				"disconnectionModalLongTitle": "已断开",
				"disconnectionModalDescription": "与服务器的连接丢失。 这可能是由于：",
				"disconnectionModalsub1": "服务器是有意关闭的。",
				"disconnectionModalsub2": "您的主机上的端口未打开。 如果是这种情况，请使用端口测试仪进行故障排除，然后再次检查防火墙或路由器。",
				"disconnectionModalCloseButton": "关闭",
				"disconnectionModalWelcomeScreenButton": "返回欢迎页面",
				"settingsLink": "设定值",
				"settingsModalLongTitle": "WebConsole 设定值",
				"showDateSettingsSwitchLabel": "在每个控制台行上显示时间",
				"readLogFileSwitchLabel": "登录后从服务器检索完整的日志文件",
				"settingsModalCloseButton": "完成",
				"players_online": "在线人数",
				"cpu_title": "CPU",
				"ram_title": "内存使用量",
				"user_title": "登录为",
				"deleteServerButton": "关闭服务器",
				"sendCommandButton": "发送"
			}
			break;
		case "ko_KR":
			lang = {
				"lang_dropdown": "언어",
				"passwordModalLongTitle": "비밀번호 필요",
				"passwordModalLabel": "비밀번호:",
				"passwordModalRememberLabel": "비밀번호 기억",
				"passwordModalCloseButton": "닫기",
				"passwordSendButton": "로그인",
				"disconnectionModalLongTitle": "연결 끊김",
				"disconnectionModalDescription": "서버와의 연결이 끊어졌습니다. 원인은 다음과 같습니다.",
				"disconnectionModalsub1": "서버가 의도적으로 닫혔습니다.",
				"disconnectionModalsub2": "호스트에서 포트가 열려 있지 않습니다. 이 경우 포트 테스터로 문제를 해결하고 방화벽이나 라우터를 다시 확인하십시오.",
				"disconnectionModalCloseButton": "닫기",
				"disconnectionModalWelcomeScreenButton": "메인으로 돌아가기",
				"settingsLink": "설정",
				"settingsModalLongTitle": "WebConsole 설정",
				"showDateSettingsSwitchLabel": "모든 줄에 시각 표시하기",
				"readLogFileSwitchLabel": "로그인 후 전체 로그 파일 읽기",
				"settingsModalCloseButton": "완료",
				"players_online": "온라인인 플레이어",
				"cpu_title": "CPU",
				"ram_title": "RAM 사용량",
				"user_title": "로그인된 사용자:",
				"deleteServerButton": "서버 삭제하기",
				"sendCommandButton": "전송"
			}
			break;
		case "fr_FR":
			lang = {
				"lang_dropdown": "Langue",
				"passwordModalLongTitle": "Mot de passe requis",
				"passwordModalLabel": "Mot de passe:",
				"passwordModalRememberLabel": "Se souvenir du mot de passe",
				"passwordModalCloseButton": "Fermer",
				"passwordSendButton": "S'identifier",
				"disconnectionModalLongTitle": "Débranché",
				"disconnectionModalDescription": "La connexion avec le serveur a été perdue. Cela peut être causé par:",
				"disconnectionModalsub1": "Le serveur a été fermé intentionnellement.",
				"disconnectionModalsub2": "Le port n'est pas ouvert sur votre hôte. Si tel est le cas, dépannez avec un testeur de port et vérifiez à nouveau votre firewall ou votre router.",
				"disconnectionModalCloseButton": "Fermer",
				"disconnectionModalWelcomeScreenButton": "Retour à la page d'accueil",
				"settingsLink": "Réglages",
				"settingsModalLongTitle": "Réglages de WebConsole",
				"showDateSettingsSwitchLabel": "Afficher l'heure sur chaque ligne de console",
				"readLogFileSwitchLabel": "Récupérer le fichier journal complet du serveur après la connexion",
				"settingsModalCloseButton": "Terminé",
				"players_online": "Joueurs en ligne",
				"cpu_title": "Utilisation de la CPU",
				"ram_title": "Utilisation de la RAM",
				"user_title": "Connecté en tant que",
				"deleteServerButton": "Supprimer le serveur",
				"sendCommandButton": "Envoyer"
			}
			break;
		case "cs_CZ":
			lang = {
				"lang_dropdown": "Jazyk",
				"passwordModalLongTitle": "Vyžadováno heslo",
				"passwordModalLabel": "Heslo:",
				"passwordModalRememberLabel": "Pamatovat si heslo",
				"passwordModalCloseButton": "Zavřít",
				"passwordSendButton": "Přihlásit se",
				"disconnectionModalLongTitle": "Odpojeno",
				"disconnectionModalDescription": "Připojení bylo ztraceno se serverem. To může být způsobeno:",
				"disconnectionModalsub1": "Server byl úmyslně uzavřen.",
				"disconnectionModalsub2": "Port není na hostiteli otevřený. Pokud tomu tak je, odstraňte problém s testerem portů a znovu zkontrolujte firewall nebo router.",
				"disconnectionModalCloseButton": "Zavřít",
				"disconnectionModalWelcomeScreenButton": "Zpět na úvodní stránku",
				"settingsLink": "Nastavení",
				"settingsModalLongTitle": "Nastavení WebConsole",
				"showDateSettingsSwitchLabel": "Zobrazit čas v každém řádku konzoly",
				"readLogFileSwitchLabel": "Po přihlášení načtěte úplný soubor protokolu ze serveru",
				"settingsModalCloseButton": "Hotovo",
				"players_online": "Počet hráčů online",
				"cpu_title": "CPU",
				"ram_title": "Využití RAM",
				"user_title": "Přihlášen jako",
				"deleteServerButton": "Odstranit server",
				"sendCommandButton": "Odeslat"
			}
			break;
		case "it_IT": //Credit to AlexZap
			lang = {
				"lang_dropdown": "Lingua",
				"passwordModalLongTitle": "Password richiesta",
				"passwordModalLabel": "Password:",
				"passwordModalRememberLabel": "Ricorda la mia password",
				"passwordModalCloseButton": "Chiudi",
				"passwordSendButton": "Login",
				"disconnectionModalLongTitle": "Disconnesso",
				"disconnectionModalDescription": "La connessione con il server è stata persa. Ciò può essere causato da:",
				"disconnectionModalsub1": "Il server è stato chiuso intenzionalmente.",
				"disconnectionModalsub2": "La porta non è aperta sul tuo host. In tal caso, risolvere i problemi con un tester della porta e controllare nuovamente il firewall o il router.",
				"disconnectionModalCloseButton": "Chiudi",
				"disconnectionModalWelcomeScreenButton": "Torna alla Home",
				"settingsLink": "Impostazioni",
				"settingsModalLongTitle": "Impostazioni WebConsole",
				"showDateSettingsSwitchLabel": "Mostra la data e l'ora per ogni linea della console",
				"readLogFileSwitchLabel": "Mostra l'intero file log dopo il login",
				"settingsModalCloseButton": "Conferma",
				"players_online": "Giocatori online",
				"cpu_title": "Utilizzo CPU",
				"ram_title": "Utilizzo RAM",
				"user_title": "Collegato come",
				"deleteServerButton": "Cancella il server",
				"sendCommandButton": "Invio"
			}
			break;
		case "nl_NL": //Credit to Twockx
			lang = {
				"lang_dropdown": "Taal",
				"passwordModalLongTitle": "Wachtwoord vereist",
				"passwordModalLabel": "Wachtwoord:",
				"passwordModalRememberLabel": "Onthoud wachtwoord",
				"passwordModalCloseButton": "Sluiten",
				"passwordSendButton": "Log in",
				"disconnectionModalLongTitle": "Verbinding verbroken",
				"disconnectionModalDescription": "De verbinding met de server is verbroken. Dit kan worden veroorzaakt door:",
				"disconnectionModalsub1": "De server is opzettelijk gesloten.",
				"disconnectionModalsub2": "De poort is niet open op uw host. Als dit het geval is, lost u het probleem op met een poorttester en controleert u uw firewall of router opnieuw.",
				"disconnectionModalCloseButton": "Sluiten",
				"disconnectionModalWelcomeScreenButton": "Terug naar homepagina",
				"settingsLink": "Instellingen",
				"settingsModalLongTitle": "WebConsole Instellingen",
				"showDateSettingsSwitchLabel": "Toon tijd op elke consolelijn",
				"readLogFileSwitchLabel": "Haal het volledige logbestand op van de server na inloggen",
				"settingsModalCloseButton": "Klaar",
				"players_online": "Spelers online",
				"cpu_title": "CPU",
				"ram_title": "RAM gebruik",
				"user_title": "Aangemeld als",
				"deleteServerButton": "Verwijder server",
				"sendCommandButton": "Stuur"
			}
			break;
		case "de_DE": //Credit to NoNamePro0
			lang = {
				"lang_dropdown": "Sprache",
				"passwordModalLongTitle": "Passwort benötigt",
				"passwordModalLabel": "Passwort:",
				"passwordModalRememberLabel": "Passwort merken",
				"passwordModalCloseButton": "Schließen",
				"passwordSendButton": "Login",
				"disconnectionModalLongTitle": "Verbindung getrennt",
				"disconnectionModalDescription": "Die Verbindung zum Server wurde unterbrochen. Dies kann verursacht werden durch:",
				"disconnectionModalsub1": "Server wurde absichtlich geschlossen.",
				"disconnectionModalsub2": "Der Port ist auf Ihrem Host nicht geöffnet. Wenn dies der Fall ist, beheben Sie die Fehlerbehebung mit einem Port-Tester und überprüfen Sie Ihre Firewall oder Ihren Router erneut.",
				"disconnectionModalCloseButton": "Schließen",
				"disconnectionModalWelcomeScreenButton": "Zurück zur Startseite",
				"settingsLink": "Einstellungen",
				"settingsModalLongTitle": "WebConsole Einstellungen",
				"showDateSettingsSwitchLabel": "Zeig die Uhrzeit vor jeder Linie in der Console",
				"readLogFileSwitchLabel": "Zeige die komplette Log nach dem Login",
				"settingsModalCloseButton": "Fertig!",
				"players_online": "Spieler Online",
				"cpu_title": "CPU",
				"ram_title": "RAM",
				"user_title": "Angemeldet als",
				"deleteServerButton": "Server entfernen",
				"sendCommandButton": "Senden"
			}
			break;
		case "tr_TR": //Credit to acarnd03
			lang = {
				"lang_dropdown": "Dil",
				"passwordModalLongTitle": "Şifre gereklidir",
				"passwordModalLabel": "Parola:",
				"passwordModalRememberLabel": "Şifremi hatırla",
				"passwordModalCloseButton": "Kapat",
				"passwordSendButton": "Oturum aç",
				"disconnectionModalLongTitle": "Bağlantı kesildi",
				"disconnectionModalDescription": "Sunucuyla bağlantı kesildi. Bunun nedeni şunlar olabilir:",
				"disconnectionModalsub1": "Sunucu kasıtlı olarak kapatıldı.",
				"disconnectionModalsub2": "Ana makinenizde bağlantı noktası açılmadı. Bu durumda, bir bağlantı noktası denetleyicisi kullanarak sorunu giderin ve güvenlik duvarınızı veya yönlendiricinizi yeniden kontrol edin.",
				"disconnectionModalCloseButton": "Kapat",
				"disconnectionModalWelcomeScreenButton": "Karşılama sayfasına geri dön",
				"settingsLink": "Ayarlar",
				"settingsModalLongTitle": "WebConsole Ayarları",
				"showDateSettingsSwitchLabel": "Her konsol satırında zamanı göster",
				"readLogFileSwitchLabel": "Giriş yaptıktan sonra tam günlük dosyasını sunucudan alın",
				"settingsModalCloseButton": "Bitti",
				"players_online": "Çevrimiçi Oyuncular",
				"cpu_title": "CPU",
				"ram_title": "RAM kullanımı",
				"user_title": "olarak giriş yapıldı",
				"deleteServerButton": "Sunucuyu silin",
				"sendCommandButton": "Gönder"
			}
			break;
		case "ja_JA": //Credit to kuroneko6423 | https://kuroneko6423.com
			lang = {
				"lang_dropdown": "言語",
				"passwordModalLongTitle": "パスワードが必要です",
				"passwordModalLabel": "パスワード:",
				"passwordModalRememberLabel": "パスワードの記憶",
				"passwordModalCloseButton": "閉じる",
				"passwordSendButton": "ログイン",
				"disconnectionModalLongTitle": "切断",
				"disconnectionModalDescription": "サーバーとの接続が切断されました:",
				"disconnectionModalsub1": "サーバーが意図的に閉じられました。",
				"disconnectionModalsub2": "ポートがホスト上で開かれていません。この場合、ポートチェッカーを使ってトラブルシューティングを行い、ファイアウォールやルーターを再確認してください。",
				"disconnectionModalCloseButton": "閉じる",
				"disconnectionModalWelcomeScreenButton": "ホームに戻る",
				"settingsLink": "設定",
				"settingsModalLongTitle": "WebConsole 設定",
				"showDateSettingsSwitchLabel": "各コンソールラインに時間を表示",
				"readLogFileSwitchLabel": "ログイン後にサーバーからフルログファイルを取得する",
				"settingsModalCloseButton": "了承",
				"players_online": "プレイヤーオンライン",
				"cpu_title": "CPU",
				"ram_title": "RAM",
				"user_title": "ログインします。",
				"deleteServerButton": "サーバーを削除",
				"sendCommandButton": "送信"
			}
			break;
		default:
			console.error("No language set");
	}

	//Set phrases
	jQuery.each(lang, (key, value) =>{
		try{
			document.getElementById(key).textContent = value;
		}catch(err){
			console.error("Cannot translate " + key + " (" + value + ")")
		}
	});

}
