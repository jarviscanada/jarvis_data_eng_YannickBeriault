# Preferred name (Characters only)
name: "Yannick Bériault"
# Your GitHub repo root URL
github_repo_root_url: https://github.com/jarviscanada/jarvis_data_eng_YannickBeriault

# Introduce yourself, your academic and professional background.
# What excites you about the software industry and your passion. At least 80 words
summary: The first thing that attracted me into computer science were databases. Their potential for organizing and making sense of any type of data, as well as the impressive elegance presented by good code, something that I discovered during my training, are still aspects of the field that are really stimulating to me. I obtained a Certificate in computer science and am working on obtaining a second, advanced Certificate (both constituted of 30 credits). I have been working as a translator for many years before transitioning into software development.

# Group your skills by level.
# Your skills can be anything software related (e.g. language, frameworks, methodologies, software, etc.).
skills:
  # At least 5 skills
  proficient:
    - Java
    - Linux/Bash
    - RDBMS/SQL
    - Agile/Scrum
    - Git
  # At least 5 skills
  competent:
    - JDBC
    - Vim
    - IntelliJ IDEA
    - JavaScript
    - HTML/CSS
    - Assembly language
    - Algorithmics and Mathematics for Computer Science
  # At least 5 skills
  familiar:
    - C
    - C++
    - React
    - PHP
    - Laravel
    - Haskell
    - Various binary encodings 

# List of Jarvis projects
jarvis_projects:
  - name: "Cluster Monitor"
    git_url: "/linux_sql"
    # The project description allows hiring managers to understand your project and technologies you used(e.g. programming languages, libraries, algorithms, hardware, tools, etc.).
    # The project description must start with an action verb (https://bit.ly/3guO98u)
    description: Implemented a monitoring agent that is used to automatically gather hardware as well as memory and CPU usage information on a system. It gathers usage info every minute, as long as the database instance is properly running. It was made using bash scripts and sql, on a virtual machine accessed through VNC viewer. It runs a PostgreSQL database inside a Docker container. It obtains its data using terminal commands, whose results it processes using a combination of simple bash commands like `grep`, `cut` and `sed`.
  - name: "Core Java Apps"
    git_url: "/core_java"
    description:
  #   - "Twitter App: Curabitur laoreet tristique leo, eget suscipit nisi. Sed in sodales ex. Maecenas vitae tincidunt dui, et eleifend quam."
      - "JDBC App: Realized a very basic implementation of a program for entering customers into a database and retrieving info about them. The database is implemented using PostgreSQL running inside a Docker container and the program makes use of JDBC and the SQL Java libraries. Dependencies where managed using Maven and version control was done through git, with Github as central repository."
      - "Grep App: Implemented a very simple version of the terminal command grep, taking as arguments a regex pattern, a root directory in which to search the files for lines containing the pattern, as well as the path to a file (pre-existing or not) in which to save the lines containing the pattern. The app uses the io and nio libraries, as well as the util.function.Predicate and util.regex libraries for matching lines, and the util.stream library to gain performance, readability and to be able to potentially process large amounts of data. It was developed using git and the IntelliJ IDE and can be launched from a jar file or from inside a docker container."
  #- name: "Springboot App"
  #  git_url: "/springboot"
  #  description: ""
  #- name: "Python Data Analytics"
  #  git_url: "/python_data_anlytics"
  #  description: ""
  #- name: "Hadoop"
  #  git_url: "/hadoop"
  #  description: ""
  #- name: "Spark"
  #  git_url: "/spark"
  #  description: ""
  #- name: "Cloud/DevOps"
  #  git_url: "/cloud_devops"
  #  description: ""

# List of personal or academic projects
# Feel free to add more projects
highlighted_projects:
    #project name
  - name: "ProjekRockola"
    #URL is optional
    # The project description must start with an action verb (https://bit.ly/3guO98u)
    description: Developped a command line based audio-player (no GUI) with elaborate smart playlist management. I decided to develop this program since no program gave me quite the smart playlist management experience I was looking for. It is written in Java, using javafx and JDBC. It makes use of an elaborate MySQL database, hosted locally. I use it every day.
  - name: "Mainframe"
    description: Put together a simple GUI interface to manage various aspects of my personnal discipline. It is written solely in Java and uses simple text files for data persistence. It is an old project that will need to be totally revamped, but it has proven to be quite dependable. I also use it every day.

#List of professional experience
#The job description allows hiring managers to understand your job and the technologies you used(e.g. programming languages, libraries, algorithms, hardware, tools, etc.).
#The description must be more than 25 words. If your experience is not software related, please focus on soft skills and achievements (e.g. team collaboration, problem-solving, improvements, communication).
professional_experience:
  #Write your Jarvis role as a professional experience rather than training.
  - title: "Software Developer"
    company: "Jarvis"
    duration: "2022-present"
    # The project description must start with an action verb (https://bit.ly/3guO98u)
    description: "I am tasked to implement and develop various applications that give me the opportunity to hone my skills with the technologies and techniques I learned in my training. I use languages such as bash, java and SQL, technologies such as virtual machines, the IntelliJ IDEA, Vim, Docker, Git and PostgreSQL, and techniques such as Agile/scrum and Gitflow."
  - title: "Evaluator"
    company: "Université du Québec à Montréal"
    duration: "2019-2021"
    description: "I evaluated students' programs written in Java, C++ and assembly language. I also had to take the time to answer students' preoccupations and questions about their grades, being firm and understanding at the same time."
  - title: "Translator"
    company: "Amazon Europe Core"
    duration: "2017-2022"
    description: "I translate products descriptions and internal documentations from English to French. I was happy to go from being employed from time to time to have access to almost all the contracts I could want through rigorous work and good evaluations from the organisation. Having the capacity to work with minimal information and try to still deliver the most appropriate result is also a skill I developed with this employer."

#List of edcuation
education: 
  - school_name: "Université du Québec à Montréal"
    duration: "2020-present"
    degree: "Advanced Certificate in Software Development"
    department: "Computer Science"
    awards_achievements:
    # optional
  - school_name: "Université du Québec à Montréal"
    duration: "2018-2020"
    degree: "Certificate in Computer Science and Software Development"
    department: "Computer Science"
    awards_achievements:

#Optional section
others:
    #category
  - title: "Other experiences & skills"
    bullets:
      - "Writing & Proofreading"
      - "Problem solving & leadership"
      - "Planning & decision making"
      - "Artistic creation"
  - title: "Activities/Hobbies"
    bullets:
      - "Creative writing"
      - "Playing music"
      - "Board games"
      - "Geopolitics and humanities"
      - "Bicycling and hiking"
      - "Camping"
  - title: "Languages spoken"
    bullets:
      - "French (native)"
      - "English (fluent)"
      - "Spanish (fluent)"
      - "German (beginner)"
