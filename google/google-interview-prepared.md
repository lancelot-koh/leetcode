# Self Introduction:
“Hi, I’m Yong. I’ve spent more than 20 years in software engineering, with experience across mobile development, backend systems, and engineering management. In recent years, my focus has been on leading engineering teams, improving engineering quality, and helping engineers grow. My leadership style is centered around building trust, giving engineers the right context, and creating systems that help teams become more independent and effective over time. That’s one of the reasons I’m excited about the opportunity at Google, because I think it aligns very well with the kind of engineering culture I enjoy working in.”

# Why leave Grab:
I really enjoyed my time there and learned a lot. I had the opportunity to work on large-scale products, lead cross-functional projects, and grow as an engineering manager.

At this stage of my career, though, I’m looking for an environment where I can have broader engineering impact, work with world-class engineers, and continue growing as a technical leader.

Google’s engineering culture, its emphasis on long-term thinking, engineering excellence, and empowering engineers really aligns with the kind of environment where I believe I can contribute the most and continue developing.


# Why Google?

There are three reasons.

First, the engineering culture. I really enjoy working in an environment where engineers are encouraged to challenge ideas, make decisions based on data, and continuously raise the engineering bar. From everything I’ve learned, that’s a big part of Google’s culture.

Second, the scale and impact. Google builds products that serve billions of users, and I enjoy solving engineering problems where a good technical decision can create impact at that kind of scale.

Finally, growth. At this stage of my career, I’m looking for a place where I can continue learning from outstanding engineers while helping other engineers grow as well. I think Google is one of the best places to do both.


# Google Engineering Manager Interview Prep

---

## Competency Coverage Map

| Competency | Primary Story | Backup | Confidence |
|---|---|---|---|
| **Ownership** | Blank Page Reduction | — | ⭐⭐⭐⭐⭐ |
| **Technical Leadership** | Blank Page Reduction | Landing Page Revamp | ⭐⭐⭐⭐⭐ |
| **Architecture & Trade-offs** | Landing Page Revamp | — | ⭐⭐⭐⭐⭐ |
| **Failure & Learning** | Landing Page Delivery Failure | — | ⭐⭐⭐⭐⭐ |
| **Coaching & Mentoring** | Scalable Onboarding System | High-performing Team | ⭐⭐⭐⭐⭐ |
| **Team Scaling** | Scalable Onboarding System | — | ⭐⭐⭐⭐⭐ |
| **Influence Without Authority** | Driving Organizational Impact | — | ⭐⭐⭐⭐⭐ |
| **Hiring Philosophy** | High-performing Team | — | ⭐⭐⭐⭐☆ |
| **Long-term Thinking** | Onboarding / UI Library | Blank Page | ⭐⭐⭐⭐⭐ |
| **Engineering Excellence** | Blank Page Reduction | Landing Page | ⭐⭐⭐⭐⭐ |

**Bonus preparation:**
- ✅ Conflict Management (Story #7 with 6-step framework)
- ✅ Difficult Feedback (included in frameworks section)
- ✅ Leadership Philosophy (5 core principles + core philosophy section)

---

## Your 6 Core Stories

---

# Story #1: Blank Page Reduction
**Primary Competencies:** Ownership, Technical Leadership, Engineering Excellence

### 20-Second Opening
We often saw it renders blank-page of marketing landing page in our merchant app, I saw engineers and QA about this issue but no one really knew what happening. Because we don't have enough logs/metric for observability. Instead of guessing, I led an engineers to improved our logging, added neccessary network tracing, and also built dashboards to collect all logs and tracing data in the network flow. After we collect 2 weeks data, we found the real issue wasn’t in the app itself, it was the API Gateway rate limiter dropping requests before they reached the backend, so there's no response from the backend. Since we found the root cause, then we worked closely with the backend team to adjust the rate limit configuration, after adopting the new change, we significantly reduce the blank-page rate from 2.5% to 0.2%, it's neary 90% better than before.

### Context (3 sentences)
- **Project:** Merchant App
- **Objective:** Reduce blank-page incidents and improve user experience
- **Challenge:** We didn’t have enough metrics or tracing to understand where requests were failing

### Three Leadership Bullets

**First — Understand the Problem**
Due to we lack of enough data, so we thought it could be the data format issue or other network issue, concurrency rendering issue, but we don't have enough data to support, then I proposed to add logging, tracing, and error handling for reporting, and build dashboards to collect and display all above data metric for investigation.

**Second — Make Decisions Based on Data**
Once we had enough data, we found a lot of request but lack of response, so I collabrated with the BE team to double confirm it. The backend team confirm is the reason is the API Gateway drops requests because of rate limiting. Finally we found the root cause and also we adjust the rate limit for improve network reliability.

**Third — Build Long-term Capability**
Initially we add logging and tracing for critical path, however I led the team continue to support more logs and error handling, We kept those monitoring all kind of error as part of our engineering platform, so the team could troubleshoot production issues much faster in future.

### Leadership Lesson
I think the biggest lesson wasn’t about find the issue is caused by the api gateway configuration. As an engineering manager, I don’t just want to fix today’s problem, we try to think for long-term. I want to build the capability so that the whole team can diagnose and solve future problems much faster, even without relying on me.

### Googleyness Alignment
✅ Ownership  
✅ Ambiguity  
✅ Data-driven Decision Making  
✅ Technical Leadership  
✅ Customer Focus  

---

# Story #2: Landing Page Revamp (Strategic Decision Making)
**Primary Competencies:** Architecture & Trade-offs, Decision Making, Technical Strategy

### 20-Second Opening
There's important project that supporting multi-store campaign management for merchants(Existing only support single store). Product manager and Design team proposed 3 options, from an engineering perspective, all of these solutions were feasible. However the real challenge was deciding how much engineering effort to invest at the stage of the product. We evaluated several options, from redesigning the data driven UI architecture to introduce a select store step to the existing creation flow. I recommended the simpler approach because it help us validate the business assumptions quickly,  meanwhile we also keeping a clear path to evolve the architecture later if it can reach to our expectation.

### Context (3 sentences)
- **Project:** Grab Merchant Platform - Multi-store Campaign Management
- **Objective:** Enable merchants to manage campaigns across multiple stores while validating business value quickly
- **Challenge:** Deciding how much engineering investment to make before we had evidence that the new business model would succeed

### Three Leadership Bullets

**First — Evaluate the Options**
The first thing for us it evaluates these options. There're three viable approaches. 

One was to redesign the architecture with a metadata-driven solution, which would be the most scalable but also the most expensive and highest risk. 

Another one is to build a completely separate workflow for this new merchant model, which was easier to implement but would introduce duplicated logic and higher maintenance costs. 

The third option was to add select store step in our existing workflow according to the user role. It wasn’t the ideal long-term architecture, but it was much faster to deliver.

**Second — Make the Decision (The Core)**
After discussing the trade-offs with the engineers,  product and upper management team, I recommended extending the existing workflow. 
It wasn’t because it was the easiest technical solution. 
It was because our priority to validate the business opportunity first, this appoach is faster than other. We didn’t yet know whether merchants would actually adopt this new capability, and the business wanted to get real customer feedback as quickly as possible. My perspective was that we should avoid making a large engineering investment until we had evidence that the product really creating value.

**Third — Plan for Evolution**
Meanwhile, I also didn’t want us to create long-term technical debt. So we chose the simpler MVP approach, we also documented what the future architecture should look like,  and agreed to justify moving to the metadata-driven design in reach out to our expection. By that way, we could move quickly in the short term without losing long-term technical direction.

### Results
- Delivered on schedule
- Enabled business validation
- Reduced implementation risk
- Allowed the product team to collect adoption data before committing to a larger architectural investment

### Leadership Lesson
That project taught an important lesson for me. As an engineering manager, choosing the right architecture isn’t just about technical elegance. It’s about balancing business uncertainty, engineering investment, and long-term maintainability. Sometimes the best decision is to build the simplest solution that allows you to learn quickly, and you prepare a clear path to evolve it later if possible.


### Googleyness Alignment
✅ Ambiguity  
✅ Decision Making  
✅ Bias for Action  
✅ Long-term Thinking  
✅ Customer Focus  

---

# Story #3: Landing Page Delivery Failure (Leading Through Failure)
**Primary Competencies:** Failure & Learning, Ownership, Execution Discipline

### 20-Second Opening
There's project related to an A/B testing for a new marketing landing page, (Design team wish us try different UX to find out which UX may bring more visibility/increase adoption etc). Early in the task, I identified some delivery risks(Chinese Golden week) and took mitigation actions(allocate additionall resource) to keep things on track. Even so, we still missed our original deadline and found quality issues during the testing stage.

### Context (3 sentences)
- **Project:** Marketing Landing Page A/B Experiment
- **Objective:** Validate several UX designs to improve merchant adoption
- **Challenge:** Tight timeline, limited engineering resources, and dependencies across multiple teams

### Three Leadership Bullets

**First — Risk Identification**
I identified the delivery risks early. For example: limited frontend capacity, a tight timeline, and dependencies on another team, and definitely I knew we could miss our target if we didn’t take action.

**Second — Mitigation Attempt**
I tried to reduce those risks. I rebalanced our engineering resources, stayed closely aligned with the backend engineering manager, and introduced regular progress reviews so we could catch problems earlier.

**Third — Reflection & Growth**
Even so, we still missed the original deadline and found quality issues during the testing stag, I realized I didn’t pay enough attention to execution after the plan was in place. I led a retrospective with the team and introduced better quality gates, earlier code reviews, more frequent cross-team communication, and clearer ownership.

### Results
- The project did miss its original delivery target (honest acknowledgment)
- More importantly, we transformed the lessons into improvements that became part of how future projects were executed
- Subsequent projects showed better delivery discipline and quality outcomes

### Leadership Lesson
My biggest takeaway is that project success doesn’t come from the initial plan. It comes from continuously checking whether execution is still aligned with the plan, and correcting course early when it isn’t.


**If asked "What's your biggest leadership failure?":**
The biggest lesson I learned is that execution needs constant attention. A project can start with a great plan, but if no one is checking whether things are still on track, small issues can grow into big ones. Since then, I’ve built more checkpoints, more visibility, and faster feedback into every project I lead.

### Googleyness Alignment
✅ Failure  
✅ Ownership  
✅ Learning  
✅ Reflection  
✅ Continuous Improvement  

---

# Story #4: Building a Structured Onboarding Framework
**Primary Competencies:** Coaching & Mentoring, Team Scaling, Long-term Thinking

### 20-Second Opening
“One example I’m proud of was improving how new engineers joined our team. When I joined Grab Ads, I noticed it typically took new engineers about a month to become productive. The onboarding experience wasn’t very structured. Documentation was scattered across different places, some of it was outdated, and new engineers relied heavily on experienced teammates whenever they got stuck.
Rather than answering the same questions every time someone joined, I wanted to build a more structured and repeatable onboarding approach that would help engineers ramp up faster and become independent much earlier.”


### Context (3 sentences)
- **Project:** Mobile engineering team onboarding
- **Objective:** Help new engineers become productive faster
- **Challenge:** No structured onboarding, outdated documentation, knowledge scattered across the team

### Three Leadership Bullets

**First — Understand the Real Problem**
Before changing anything, I wanted to understand why onboarding was taking so long. After talking to the team and observing new joiners, I realized the problem wasn’t that people were unwilling to help. The real issue was that knowledge was scattered, there wasn’t a clear learning path, and every new engineer was asking the same questions repeatedly.
To me, this wasn’t an individual problem—it was an opportunity to improve the team’s way of working.


First — Create a Structured Learning Path
“Instead of creating more documents, I designed a structured onboarding framework. It provided a clear learning path, covering the system architecture, development workflow, CI/CD pipeline, coding practices, key stakeholders, and how our team collaborated.”

Second — Centralize and Improve Knowledge
“I also consolidated the existing documentation into one place and updated outdated content. The goal was to make it easy for new engineers to find information on their own instead of depending on senior engineers for every question.”

Third — Validate and Continuously Improve
“I started by using the framework within my own team. As new engineers joined, I collected feedback, refined the content, and continued improving the onboarding experience. My goal wasn’t to create a perfect process on day one, but to build something practical that we could improve over time.”

### Results
As a result, the onboarding time dropped from around one month to about two weeks. 
More importantly, new engineers became productive much earlier, started contributing independently, and senior engineers spent much less time answering repetitive onboarding questions. 
The team became more efficient without sacrificing the quality of onboarding.”


### Leadership Lesson
This experience reinforced something I strongly believe today. Good onboarding isn’t just about sharing knowledge—it’s about creating an environment where engineers can learn independently and build confidence quickly. As an engineering manager, I like building repeatable practices that make the whole team more effective, not just solving the same problem one person at a time.

### Googleyness Alignment
✅ Long-term Thinking  
✅ Ownership  
✅ Continuous Improvement  
✅ Helping Others Succeed  
✅ Building Systems  

---

# Story #5: Driving Organizational Impact Beyond My Team’s Scope
**Primary Competencies:** Influence Without Authority, Organizational Leadership, Long-term Thinking

### 20-Second Opening
One example is our shared UI component library. It was owned by the Platform team, but it was missing many commonly used components, so adoption across product teams was quite low. As a result, different teams were building similar components themselves.

Even though it wasn’t on my team’s roadmap, I thought improving the shared library would have a much bigger impact than each team working in isolation. So I approached the Platform team and suggested that we contribute engineering resources to help expand the library.

### Context (3 sentences)
- **Project:** Shared Mobile UI Component Library maintained by the Platform Team
- **Objective:** Help improve the shared engineering platform so product teams could reuse more UI components
- **Challenge:** Neither I nor my team owned the platform, and contributing meant investing engineering effort outside our committed product roadmap—how do I justify that investment?

### Three Leadership Bullets

**First — Identify an Organizational Opportunity**
First, I tried to look beyond my own team’s backlog. I noticed that different product teams were building very similar UI components over and over again. It wasn’t just duplicated work—it also led to inconsistent user experiences. I felt that if we invested in the shared component library, the whole engineering organization would benefit.

**Second — Build Partnership & Align Your Team**
The next challenge was getting everyone aligned. I first spoke with the Platform team to understand where our contribution would have the biggest impact. Then I talked to my own team. Since we didn’t own the library, I needed to explain why it was worth spending our time on it. My point was simple: if we build a reusable component once, every team—including ours—can reuse it instead of rebuilding it again and again.


**Third — Deliver Value at Scale**
Once we started contributing, we worked closely with the Platform and Design teams. We added new reusable components, improved existing ones based on the latest design requirements, and kept collecting feedback from product teams. As the library became more complete, more teams naturally started adopting it.

### Results
- Coverage increased from 52% to 67%
- My team contributed 12–13 reusable components
- Multiple teams benefited from a more complete component library
- Reduced duplicated implementation effort across the organization
- Improved UI consistency across products

### Leadership Lesson
That experience taught me that the best engineering leaders don’t just build features for their own team—they build capabilities that help every team move faster.

### Follow-up You’ll Prepare For
**Q: “Why did you spend your team’s time on another team’s project?”**
- A: “We were repeatedly building similar UI components ourselves, and so were other teams. By investing in the shared library once, we reduced duplicated effort for everyone, including our own team, and created long-term engineering efficiency. I believed that was a better use of engineering time than continuing to solve the same problem independently.”
- **Pattern:** Long-term thinking, organizational impact, ownership beyond formal scope

### Googleyness Alignment
✅ Long-term Thinking  
✅ Ownership (beyond formal scope)  
✅ Collaboration  
✅ Driving Engineering Excellence  
✅ Organizational Leadership

---

# Story #6: High-performing Engineering Team
**Primary Competencies:** Building High-performing Teams, Coaching, Hiring Philosophy

### 20-Second Opening
One thing I’m proud of is helping build a team where engineers could become productive quickly and grow independently. I believe building a high-performing team starts with hiring the right people, but it doesn’t stop there. Onboarding, mentoring, and creating the right environment are just as important. My goal has always been to help engineers become successful, not just get them through the door.

### Context (3 sentences)
- **Project:** Mobile engineering team leadership
- **Objective:** Build a team where engineers are productive, growing, and independent
- **Challenge:** Creating this environment requires consistent investment across hiring, onboarding, mentoring, and culture

### Three Leadership Bullets

**First — Hire for Long-term Potential**
First, I focus on hiring the right people. Of course technical skills are important, but they’re not the only thing I look for. I also look for ownership, curiosity, a willingness to learn, and how well they work with others. I actually enjoy hiring people who are stronger than me in certain areas, because a great team should have different strengths, not people who all think the same way.

**Second — Build Trust Early**
Second, I think the interview goes both ways. I always spend some time explaining how our team works, what we value, and what it’s really like to work with us. I want candidates to decide whether we’re the right team for them, just like we’re deciding whether they’re the right fit for us. I think trust starts before day one.

**Third — Grow People**
Finally, I focus on helping people grow. I remember one junior engineer we hired. With a structured onboarding plan and regular one-on-ones, he became productive in about two weeks. A few months later, he was delivering features on his own and even built several reusable UI components that other teams started using. Eventually, the Component Engineering team recognized his work and invited him to become one of the core contributors to the shared component library. For me, that’s one of the most rewarding parts of being a manager.

### Results
Overall, we saw much faster onboarding—from around a month to about two weeks. 
Engineers became independent more quickly, started contributing across teams, and their work was recognized by other engineering teams.

### Leadership Lesson
The biggest lesson for me is that an engineering manager’s job isn’t to write the best code. It’s to help other engineers succeed. If I can give people the right context, support them as they grow, and create an environment where they can work independently, the whole team becomes stronger. And when the team grows, the organization grows as well.


### Googleyness Alignment
✅ Leadership  
✅ Long-term Thinking  
✅ Helping Others Succeed  
✅ Ownership  

---

## Your Leadership Philosophy

Use this when you don't have a perfect story that fits the question.

### Principle 1: Build Trust First
I believe trust is the foundation of every high-performing team. Transparent communication, respect, and psychological safety enable people to do their best work.

### Principle 2: Provide Context
I don't like simply assigning tasks. I want engineers to understand the business context and trade-offs so they can make good decisions independently.

### Principle 3: Grow Independent Engineers
My goal isn't to solve every problem myself. It's to develop engineers who can solve problems independently and help others grow.

### Principle 4: Engineering Excellence
I encourage high standards through code reviews, architecture discussions, monitoring, documentation, and continuous improvement.

### Principle 5: Build Systems, Not Heroes
Instead of solving the same problem repeatedly, I prefer building scalable processes that help the entire organization.

---

## L6 Thinking Patterns (How to Think, Not How to Talk)

Google isn't looking for fancy language. They're looking for **mature thinking patterns**. Use simple words, but demonstrate these patterns:

### 1. **Evidence First, Decisions Second**
- ❌ "I decided to do X"
- ✅ "I wanted to understand the problem first, so I gathered data. That showed us..."
- **Why:** Shows you don't guess; you investigate before deciding

### 2. **Always Ask "Why" First**
- ❌ "We built better documentation"
- ✅ "Before I created documentation, I talked to new engineers to understand what was actually slowing them down. I found three root causes..."
- **Why:** Shows structured thinking, not jumping to solutions

### 3. **Trade-offs Over Perfection**
- ❌ "We chose the best architecture"
- ✅ "We had three options with different trade-offs. Option A was perfect but risky. Option B was safer but slower. We chose B because at that stage of the product, speed to validate was more important than architectural perfection."
- **Why:** Shows business judgment, not just technical skill

### 4. **System Thinking Over One-off Fixes**
- ❌ "I onboarded the new engineer well"
- ✅ "Instead of just helping this one engineer, I realized the problem was systematic. So I built an onboarding system that helps every future engineer."
- **Why:** Shows you think about organizational impact, not just immediate problems

### 5. **Learning > Success**
- ❌ "We succeeded"
- ✅ "We failed to deliver on time, and that taught me something important: identifying risks isn't enough. I needed to continuously inspect execution."
- **Why:** Shows self-awareness and growth mindset

### 6. **Ownership Across Boundaries**
- ❌ "The other team should have done X"
- ✅ "Even though I didn't have direct authority, I saw a problem I could help solve. So I..."
- **Why:** Shows you don't hide behind org structure; you take initiative

### 7. **Long-term > Short-term**
- ❌ "We shipped the feature"
- ✅ "We shipped the feature quickly, but we also thought about how this decision would affect future engineers. So we documented the architecture and set clear signals for when we'd revisit this decision."
- **Why:** Shows you think beyond the next sprint

---

## Follow-up Q&A for Each Story

### Story #1: Blank Page Reduction

**Q: Why did you decide to improve observability first instead of immediately fixing the app?**
- A: "I could have guessed where the problem was, but that would have wasted time investigating the wrong thing. I wanted evidence first. Once we had dashboards and tracing, the real cause became obvious—it was actually the gateway, not the app. That completely changed our investigation."
- **Pattern:** Evidence-first thinking

**Q: How did you convince other teams to support the investigation?**
- A: "It wasn't about convincing. Once I showed them the dashboards and we identified it was their gateway, they were motivated to fix it because it was their problem too. The data made the case."
- **Pattern:** Evidence-based persuasion, not politics

**Q: What would you do differently if this happened again?**
- A: "I'd probably invest in observability earlier, before we had blank-page incidents. Now I think observability is a leadership investment, not just a support tool. I'd propose it proactively."
- **Pattern:** Learning from experience, long-term thinking

---

### Story #2: Landing Page Revamp

**Q: How did you decide between those three options?**
- A: "I evaluated each one against three criteria: delivery speed, engineering investment, and long-term scalability. But the key factor was: what do we know and not know? We didn't know if merchants would adopt the feature. So spending 3 months on the perfect architecture made no sense. We needed to validate first."
- **Pattern:** Trade-off analysis tied to business context

**Q: What if they'd chosen Option 1 instead?**
- A: "That would have been fine if the business priority was different. But given that we were validating a new business model, the faster option made more sense. The important thing wasn't the choice—it was the reasoning."
- **Pattern:** Shows you're flexible, not dogmatic

**Q: Did you revisit that decision later?**
- A: "Yes, once merchants adopted it, we had the data to justify the bigger architectural investment. We documented clear signals upfront about when we'd make that investment, so the decision wasn't arbitrary."
- **Pattern:** Long-term planning, clear decision criteria

---

### Story #3: Landing Page Delivery Failure

**Q: What warning signs did you miss?**
- A: "I focused on resource allocation and identified the high-level risks. But I didn't have enough visibility into daily progress and quality trends. I was checking in weekly, not daily. By the time I saw the problem, it was too late to fix."
- **Pattern:** Honest about gaps, specific about what to improve

**Q: Why didn't your mitigation work?**
- A: "Mitigating resource constraints isn't the same as managing execution quality. I thought if I allocated enough people, we'd succeed. But execution quality also depends on communication, code reviews, and continuous inspection. That's on me."
- **Pattern:** Takes ownership, doesn't blame external factors

**Q: How did your team react?**
- A: "They were frustrated, but they trusted me because I didn't try to hide the failure or blame them. In the retrospective, we focused on 'how do we do better next time' instead of 'who messed up.'"
- **Pattern:** Leadership through failure, building trust

---

### Story #4: Scalable Onboarding System

**Q: How did you know onboarding was the real problem?**
- A: "I didn't assume. I talked to new engineers and asked them: What slowed you down? What information did you need? Who did you ask? That's when I discovered it was a system problem, not individual engineers being slow."
- **Pattern:** Investigate before solving

**Q: How did you keep the documentation up to date?**
- A: "That's a great question. We didn't—it became stale again. What actually worked was making onboarding a shared responsibility. Each team now owns their part, and we review it quarterly. The system works better when ownership is distributed."
- **Pattern:** Honest about imperfection, iterative improvement

**Q: How do you measure success?**
- A: "Time to productivity went from 4 weeks to 2 weeks. But the real measure was independence—new engineers could solve problems without asking senior engineers. We tracked questions in Slack and saw a clear drop."
- **Pattern:** Uses multiple metrics, not just one number

---

### Story #5: UI Component Standardization

**Q: Why weren't teams adopting the library?**
- A: "I asked them. The answers were: missing components, existing components weren't flexible, and migration seemed harder than building from scratch. Once I understood those barriers, I could address them."
- **Pattern:** Curiosity before judgment

**Q: How did you convince them?**
- A: "I didn't convince them. I reduced the friction by building what they needed and making it easier to use. Adoption followed because the value was obvious, not because I asked nicely."
- **Pattern:** Influence through value creation, not persuasion

**Q: What resistance did you encounter?**
- A: "Some teams had already invested in their own components. They weren't going to throw that away. So we compromised—we made the shared library flexible enough to support their use cases. That took longer but was worth it."
- **Pattern:** Pragmatic, looks for win-win solutions

---

### Story #6: High-performing Engineering Team

**Q: What do you look for when hiring?**
- A: "Growth mindset and ownership matter more than current skills. I want people who ask good questions, admit when they don't know something, and take initiative. Technical fundamentals matter, but I can teach someone a new technology. I can't teach curiosity."
- **Pattern:** Thinks about potential, not just current skills

**Q: How do you know someone will grow?**
- A: "I look at how they talk about past projects. Do they ask questions? Do they learn from mistakes? Do they help other people? Those behaviors predict growth better than test scores."
- **Pattern:** Behavioral signals over credentials

**Q: How do you measure success after hiring?**
- A: "Productivity is the first measure—are they independent? But the deeper measure is: are other people learning from them? And: are they taking on bigger problems over time? If all three are yes, the hire worked."
- **Pattern:** Multi-dimensional assessment

---

## Red Flags to Avoid (Things That Hurt L6 Candidates)

### 🚩 **Blaming External Factors**
- ❌ "The other team was slow" / "We didn't have enough resources" / "The market changed"
- ✅ "We misjudged the timeline" / "I didn't have enough visibility" / "I should have communicated sooner"
- **Why:** L6 takes ownership, doesn't make excuses

### 🚩 **Focusing Only on Technical Solutions**
- ❌ "We built a better architecture"
- ✅ "We improved the architecture AND we changed how the team communicates AND we built processes to catch issues earlier"
- **Why:** L6 leaders solve organizational problems, not just technical ones

### 🚩 **Saying You Did It Alone**
- ❌ "I rebuilt the system from scratch"
- ✅ "I worked with three teams to redesign the system"
- **Why:** L6 leads through others, not individual heroics

### 🚩 **No Learning/Growth**
- ❌ "We delivered successfully" (end of story)
- ✅ "We delivered, and here's what I learned that changed how I lead..."
- **Why:** L6 candidates are always learning and improving

### 🚩 **Vague Measurements**
- ❌ "We improved performance"
- ✅ "We reduced latency from 500ms to 200ms, and that translated to a 15% increase in conversion"
- **Why:** Data-driven thinking is L6 requirement

### 🚩 **Focusing on Your Success vs Team/Org Success**
- ❌ "I was promoted" / "My project won an award"
- ✅ "My team became more independent" / "The organization adopted our process"
- **Why:** L6 cares about impact beyond themselves

### 🚩 **Talking Down to People**
- ❌ "I had to teach the PM what engineering constraints mean"
- ✅ "I explained the trade-offs so the PM could make an informed decision"
- **Why:** L6 shows respect and builds partnerships

### 🚩 **No Trade-off Thinking**
- ❌ "We chose the best solution"
- ✅ "We chose the solution that made sense given X constraint and Y business goal"
- **Why:** Real decisions always involve trade-offs. Saying "best" sounds naive

### 🚩 **Not Asking Clarifying Questions**
- ❌ "The PM said we needed feature X, so I built feature X"
- ✅ "The PM wanted feature X. Before I started, I asked: What problem are we solving? Who's the user? What's success? Those answers shaped a different solution."
- **Why:** L6 leaders question assumptions

### 🚩 **Overcomplicating Simple Stories**
- ❌ "Let me tell you about the microservices architecture and the Kubernetes setup..."
- ✅ "We had a performance problem, so we restructured how services communicate."
- **Why:** Complexity hides the real story. Google wants clarity, not technical jargon


### Conflict Management
**When you don't have a perfect example:**
> "Fortunately, I haven't had many serious interpersonal conflicts because I invest a lot in building trust and setting clear expectations early. However, technical disagreements happen regularly, and I actually think they're healthy."

**Your framework:**
1. **Understand** — What are we actually disagreeing about? Requirements? Timeline? Quality? Architecture?
2. **Align on the goal** — Before debating solutions, make sure everyone agrees on what success looks like
3. **Use evidence** — Encourage data, experiments, customer impact, and technical trade-offs rather than opinions
4. **Commit** — Once we've made a decision, expect everyone (including yourself) to support it fully

### Difficult Feedback & Coaching
**When asked about difficult feedback:**
> "I believe good feedback should be timely, specific, and actionable. I always start by understanding the situation before giving advice."

**Your framework:**
1. **Observe** — Notice the behavior or outcome
2. **Understand** — Ask questions before jumping to conclusions
3. **Specific Feedback** — Frame as impact, not judgment ("I noticed stakeholders were confused about project status" vs "You communicate badly")
4. **Action Plan** — Agree on one or two concrete improvements together
5. **Review** — Check progress in regular one-on-ones



### Building High-performing Teams (Comprehensive Answer)
**Opening:**
> "For me, a high-performing team isn't simply a team with strong engineers. It's a team where people trust each other, understand the business goals, take ownership, and continue learning."

**Your framework:**
1. **Hire Carefully** — Look for growth mindset, ownership, learning agility, collaboration, complementary strengths, and technical fundamentals
2. **Build Trust** — Transparent communication, respect, psychological safety
3. **Give Context** — Share business goals, architecture decisions, and trade-offs
4. **Grow People** — Regular one-on-ones, coaching, stretch opportunities
5. **Continuously Improve** — Retrospectives, knowledge sharing, engineering excellence

### What Makes a Great Engineering Manager?
**Your answer:**
> "I think a great engineering manager creates impact through people rather than individual technical contributions."

**Three bullets:**
- **Build the right team** — Hire people with strong potential and complementary strengths
- **Create the right environment** — Trust, ownership, psychological safety, and engineering excellence
- **Help people grow** — Provide context, coach, remove blockers, develop future leaders

---

## Questions to Ask the Interviewer

**Why ask questions?**
- Shows you're thinking strategically about the role
- Demonstrates you care about team health, not just the title
- Lets you evaluate Google as much as they evaluate you
- L6 leaders are thoughtful about what they're signing up for

**Best time to ask:** At the very end when they say "Do you have any questions for me?"

### Strategic Questions (About the Team/Role)

**Q1: "What does success look like for this role in the first year?"**
- **Why:** Shows you're thinking about outcomes, not just tasks
- **What to listen for:** Clarity, measurable goals, alignment with your values
- **Red flag if they say:** "Just keep things running" (means no growth opportunity)

**Q2: "What's the biggest challenge facing this team right now?"**
- **Why:** Shows you're interested in real problems, not just the sunny version
- **What to listen for:** Honesty, specific technical/org challenges, not complaints
- **How to use:** If it's something you have experience with, you can share relevant insight

**Q3: "Can you tell me about the engineers on this team? What are their strengths and what are they working on?"**
- **Why:** Shows you care about knowing the people, not just the org structure
- **What to listen for:** Do they know their team well? Are people diverse in skills/backgrounds?
- **Red flag if they say:** "There's a lot of turnover" without acknowledging it's a problem

**Q4: "How would you describe the engineering culture on this team?"**
- **Why:** Shows you care about environment, not just metrics
- **What to listen for:** Values like ownership, learning, collaboration vs "we ship fast"
- **This reveals:** Whether your leadership style matches their culture

### Stakeholder & Impact Questions

**Q5: "Who are the key stakeholders I'd work with—product, design, other engineering teams?"**
- **Why:** Shows you think about cross-functional impact
- **What to listen for:** Clear partnerships vs siloed teams
- **Red flag if they say:** "You mainly work with product" (means limited scope)

**Q6: "What's the relationship like between engineering and product on this team?"**
- **Why:** Shows you care about PM-EM partnership quality
- **What to listen for:** Mutual respect, shared goals, or tension/conflict
- **How to use:** If there's tension, you can ask how you'd help bridge it

**Q7: "What metrics matter most to this team? How do you measure engineering productivity?"**
- **Why:** Shows you think data-driven
- **What to listen for:** Mix of speed, quality, impact—not just velocity
- **Red flag if they say:** "We measure lines of code" (immature metrics)

### Learning & Growth Questions

**Q8: "How does this team invest in growing engineers? Do people get opportunities to lead?"**
- **Why:** Shows your philosophy aligns with growing talent
- **What to listen for:** Concrete examples, not just "we believe in growth"
- **How to use:** This tells you if your coaching/mentoring will be valued

**Q9: "What's your retention like? Where do people go when they leave?"**
- **Why:** Shows you care about team stability and growth paths
- **What to listen for:** If people move up internally vs out entirely
- **Red flag if they say:** "People leave for startups" without acknowledging it's normal

### Organizational Context Questions

**Q10: "What's the relationship between this team and the broader org? How autonomous is the team?"**
- **Why:** Shows you understand organizational dynamics
- **What to listen for:** Autonomy level, support from leadership, clear decision-making
- **Red flag if they say:** "Everything requires approval from multiple layers"

**Q11: "How are decisions made on this team? Is there a clear framework?"**
- **Why:** Shows you care about how power/authority works
- **What to listen for:** Data-driven, clear ownership, not politics
- **How to use:** Later you can reference this when you make decisions

### Honest/Direct Questions (Show You're Evaluating Them Too)

**Q12: "What do you wish you had known when you joined this team?"**
- **Why:** Shows you want real talk, not marketing speak
- **What to listen for:** Honesty, humility, specific challenges
- **This reveals:** Whether the interviewer is reflective and self-aware

**Q13: "What would a strong engineering manager accomplish that hasn't been done yet?"**
- **Why:** Shows you're thinking about impact opportunities
- **What to listen for:** Clear gaps, strategic thinking, not just firefighting
- **How to use:** If you have relevant experience, offer perspective


## Sample Question Flow

**If the interviewer asks "Do you have any questions?" — pick 3-4 from above:**

**Order matters:**
1. Start with **strategic role question** (Q1 or Q2)
2. Then **team/culture question** (Q3 or Q4)  
3. Then **learning question** (Q8)
4. End with **honest/reflective question** (Q12)

**Example:**
> "I do have a few questions, if that's okay. First, what does success look like for this role in the first year? And then I'm curious: what's the biggest challenge facing your team right now? And finally, what do you wish you had known when you joined this team?"

This shows you're strategic, curious, and evaluating them too.

**Your biggest strengths:**
- Clear, data-driven decision-making
- Long-term systems thinking
- Ability to influence without authority
- Coaching and growing engineers
- Learning from failure

## Quick Reference: Your Signature Strengths

1. **Data-driven Decision Making** — Everything you do is grounded in evidence and measurement
2. **Strategic Architecture Thinking** — You balance technical excellence with business reality
3. **Building Scalable Systems** — You solve problems in ways that help the entire organization
4. **Growing Independent Engineers** — Your goal is engineers who can think, decide, and lead
5. **Learning from Failure** — You treat setbacks as opportunities to improve your leadership
---

## Story #7 (Optional): Handling Disagreement & Conflict

**When to use:** “Tell me about a time you handled disagreement” / “Tell me about conflict” / “Describe a time you changed your mind”

### Your Conflict Leadership Philosophy
I don’t see disagreement as a problem. In a team of strong engineers, respectful technical debate is healthy. My role is to create an environment where different perspectives can be discussed openly and then help the team converge on the best decision based on evidence and our shared goals.

### Your Conflict Management Framework

**Step 1: Create a Safe Environment**
From the beginning, make it clear: we review ideas, not judge people. Encourage everyone to challenge assumptions, including yours.

**Step 2: Understand the Disagreement**
What are we actually disagreeing about? Goals? Priorities? Architecture? Trade-offs? Don’t assume.

**Step 3: Align on the Shared Objective**
Before debating solutions, make sure everyone agrees on what problem we’re solving and what success looks like.

**Step 4: Evaluate Using Evidence**
Encourage data, experience, experiments, technical principles, and customer impact—not opinions or seniority.

**Step 5: Commit Together**
Once we decide, everyone (including yourself) supports the decision fully.

**Step 6: Review Later**
If new evidence appears, we’re willing to adjust.

## Core Leadership Philosophy (Across All Stories)

**The unifying theme of your leadership:**
Create an environment where good engineering decisions naturally happen.

**How you do this:**
1. Give people context
2. Build trust
3. Encourage open discussion
4. Make decisions based on evidence
5. Help engineers become independent
6. Improve systems for the future

**Your signature phrase (use in almost every People Management answer):**

I don’t see disagreement as a problem. In a team of strong engineers, respectful technical debate is healthy. My role is to create an environment where different perspectives can be discussed openly and then help the team converge on the best decision based on evidence and our shared goals.


=================================


For the People Management Interview

1. What does success look like?

“If someone joins this role and is highly successful after the first year, what would you expect them to have accomplished?”

Why it’s good:

* Shows you’re outcome-oriented.
* Helps you understand expectations.
* Opens discussion about priorities.

⸻

2. Team Growth

“What’s the biggest challenge you’re facing today in growing or scaling the engineering team?”

This is an EM question.

⸻

3. Team Capability

“Are there any engineering capabilities or leadership skills that you feel the team is currently trying to strengthen?”

This lets you connect your onboarding, coaching, and engineering excellence stories.

⸻

4. Engineering Culture

“What characteristics do your strongest engineering managers have in common?”

Excellent question.

------
“As an engineering manager, one thing I care a lot about is helping engineers become increasingly independent in their decision-making. How does your team encourage that kind of growth while still maintaining high engineering quality and consistency?”


===============================================================

For Googleyness & Leadership

1. Decision Making

“What kinds of leadership behaviors tend to differentiate successful engineering managers at Google?”

This often leads to a discussion about culture.

⸻

2. Team Dynamics

“What are the biggest organizational or collaboration challenges this team is working through today?”

This is much better than asking about technology.

⸻

3. First Six Months

“If I were to join the team, what would be the highest-impact problem you’d hope I could help solve during the first six months?”

Very strong.


=================================================
AI:
“With generative AI evolving so quickly, how does the team balance building long-term engineering foundations while still moving fast enough to support new product capabilities?”




==========================================================

For Google L6, if they ask:

“What’s your biggest strength?”
“I think one of my biggest strengths is system thinking. When I see a problem, I naturally try to understand the root cause instead of fixing the symptoms. I also like building long-term solutions rather than solving the same problem repeatedly.”

Example:

“For example, in our blank-page investigation, instead of trying different fixes, I first invested in improving observability. That allowed us to identify the real root cause, and we kept those monitoring capabilities so the whole team could diagnose future incidents much faster.”

Finish with:

“I think that approach has shaped how I lead teams as well. I try to build systems and capabilities, not just solve individual problems.”


“What’s your leadership strength?”
Example:

“One area I really enjoy is helping engineers grow. I like giving people enough context to make decisions independently instead of simply assigning tasks.”


Biggest Weakness
Early in my management experience, I sometimes assumed that once a good plan was in place, execution would naturally stay on track.
Example:

“One project that taught me this was an A/B testing project where we missed our delivery target. I identified the risks early and adjusted resources, but I wasn’t inspecting execution closely enough throughout the project.”

Then:

“Since then, I’ve introduced more frequent checkpoints, earlier code reviews, and regular progress reviews. Today I’m much more proactive about monitoring execution instead of assuming everything is progressing as planned.”

--------------------

“Earlier in my career, I tended to communicate in too much detail, especially when discussing technical topics.”

Then:

“Over time, I realized different audiences need different levels of detail. Today I usually start with the high-level picture, check whether we’re aligned, and only go deeper if needed.”

For example.
Strength

“I think one of my biggest strengths is system thinking. I naturally try to solve the underlying problem instead of treating the symptoms. For example, during our blank-page investigation, rather than immediately changing the application logic, I first invested in improving observability. That helped us identify the real root cause and gave the whole team better tools for future incidents. That’s how I generally approach engineering leadership as well.”

⸻

Weakness

“One area I’ve consciously worked on is execution management. Earlier in my management experience, I sometimes focused too much on planning and risk identification, assuming execution would naturally follow. One project where we missed a deadline really changed my perspective. Since then, I’ve introduced regular execution checkpoints, earlier feedback loops, and stronger quality reviews. It’s made me much more effective at keeping projects on track.”

⸻

Strengths

1. System thinking → Blank Page story.
2. Growing engineers → Onboarding / Junior Engineer story.
3. Long-term engineering mindset → UI Component Library story.

Weaknesses

1. Execution monitoring → Landing Page Failure story.
2. Tailoring communication to different audiences → Your communication improvement (high-level first, details on demand).