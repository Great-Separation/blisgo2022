@echo off

rmdir /s /q C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\static\assets

rem erase /Q C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\static\manifest.json

erase /Q C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\static\offline.html

erase /Q C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\static\sitemap.xml


move /y C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\templates\assets C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\static

move /y C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\templates\manifest.json C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\static

move /y C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\templates\sitemap.xml C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\static

move /y C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\templates\cmmn\offline.html C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\static

move /y C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\static\assets\js\cmmn\pwabuilder-sw.js C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\static

erase /Q C:\Users\okjae\IdeaProjects\blisgo\src\main\resources\templates\*