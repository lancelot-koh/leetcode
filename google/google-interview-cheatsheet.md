# Google L6 EM Interview — Cheat Sheet

---

# 📄 PAGE 1: Your 6 Stories

### Story #1: Blank Page Reduction
**Opening:** So we started seeing blank pages in our app, and honestly, we had no idea what was causing them because we didn't have enough visibility. Instead of just guessing, I took ownership and led an investigation to gather actual evidence.

**Three Actions:**
1. **Understand the Problem** → I worked with the team to build logging, tracing, and dashboards so we could see the complete request flow
2. **Make Decisions Based on Data** → Once we had the data, it showed us something surprising—the API Gateway rate limiter was dropping requests, not the app
3. **Build Long-term Capability** → I didn't just want to fix this one issue. We kept all those monitoring systems as part of our platform so the team could troubleshoot production issues way faster in the future

**Result:** Blank pages went from 2.5% down to 0.1%. Now the team can diagnose issues independently.

**Key Phrase:** "Observability is a leadership investment"

---

### Story #2: Landing Page Revamp
**Opening:** We needed to build multi-store campaign management for merchants. The thing is, we had to make a choice about how much engineering effort to invest before we knew if the business model would actually work.

**Three Actions:**
1. **Evaluate the Options** → We looked at three approaches: build a brand new metadata-driven architecture (scalable but expensive and risky), build a separate workflow (easier but duplicated), or just extend what we had (fastest but not ideal long-term)
2. **Make the Decision** → I recommended we go with the simplest approach. Not because it was easiest technically, but because we didn't know yet if merchants would adopt this. The business needed to validate the concept quickly.
3. **Plan for Evolution** → But here's the key thing—we didn't just ship and forget about it. We documented what the better architecture should look like eventually and identified the clear signals that would tell us when to make that bigger investment

**Result:** Delivered on schedule, enabled the business to validate the model, reduced risk.

**Key Phrase:** "Balance technical excellence with business uncertainty"

---

### Story #3: Delivery Failure
**Opening:** I led an A/B testing project for a marketing landing page. I identified the risks early, I allocated resources, I tried to mitigate everything. But honestly, we still missed the deadline and shipped with quality issues we shouldn't have had.

**Three Actions:**
1. **Risk Identification** → Early on, I recognized we had delivery risks—limited frontend capacity, tight timeline, dependencies on another team
2. **Mitigation Attempt** → So I rebalanced resources, aligned closely with the backend manager, and introduced regular progress reviews to try to catch problems earlier
3. **Reflection & Growth** → Even with all that, we still missed the deadline. Looking back, I realized my mistake. I focused too much on identifying risks upfront but didn't pay enough attention to monitoring execution quality continuously throughout the project

**Result:** We missed the original deadline (and I'm honest about that), but we learned a huge lesson that changed how I manage projects

**Key Phrase:** "Identifying risks isn't enough"

---

### Story #4: Scalable Onboarding System
**Opening:** When I joined the team, I noticed new engineers took almost a month to become productive. Documentation was all over the place, outdated, and people relied heavily on asking experienced teammates for help.

**Three Actions:**
1. **Understand the Real Problem** → Before I built anything, I talked to new engineers and asked them what actually slowed them down. I found the knowledge was scattered, there wasn't a clear learning path, and everyone was asking the same questions over and over
2. **Build a Scalable System** → So instead of writing random documents, I designed a structured framework that covered team structure, architecture, key stakeholders, how we work, and a clear learning roadmap. I also centralized all the documentation in one place so new people had everything in one spot
3. **Build Engineering Culture** → I wanted onboarding to teach not just technical stuff but also our engineering culture—how we actually collaborate, how we make decisions, what we value as a team

**Result:** We went from one month to two weeks for onboarding. New engineers became independent faster. The documentation became a shared team asset instead of scattered docs.

**Key Phrase:** "Onboarding is creating environment, not just transferring information"

---

### Story #5: Driving Organizational Impact Beyond My Team's Scope
**Opening:** The Platform team owned a shared UI component library, but the adoption across product teams was really low. This wasn't part of my team's roadmap and nobody asked us to contribute, but I saw an opportunity to improve the whole organization.

**Three Actions:**
1. **Identify Organizational Opportunity** → I noticed different teams were repeatedly building similar UI components, which meant duplicated work and inconsistent user experiences. I believed that investing in the shared platform would create way more long-term value
2. **Build Partnership & Align Your Team** → I reached out to the Platform team to understand what they needed and where our contribution would have the most impact. Then I had to convince my own team that spending time on a platform they didn't own was still the right investment
3. **Deliver Value at Scale** → We contributed about 12 to 13 core components, improved existing ones based on new design requirements, and worked closely with both Platform and Design to make the library more complete and easier to adopt

**Result:** Component coverage went from 52% up to 76%. Multiple teams benefited. We reduced duplicated effort across the entire organization.

**Key Phrase:** "Reduce friction by creating value, not by asking people to change"

---

### Story #6: High-performing Engineering Team
**Opening:** For me, building a high-performing team isn't just about hiring really smart engineers. It's about creating an environment where people understand the context, take ownership, and actually grow over time.

**Three Actions:**
1. **Hire for Long-term Potential** → When I interview, I obviously look at technical skills, but that's only part of it. I focus on growth mindset, ownership, collaboration, and learning ability. I actually like hiring people who are better than me in certain areas because that means the team complements each other's strengths
2. **Build Trust Early** → During interviews, I spend time explaining how our team actually works and what we value. I want candidates to evaluate us just as much as we're evaluating them. Trust doesn't start on day one—it starts during the interview process
3. **Grow People** → One example is a junior engineer we hired. Using the onboarding framework and regular one-on-ones, he became productive in about two weeks. Pretty quickly, he wasn't just fixing bugs—he was delivering features independently, even contributed reusable components that other teams adopted. Eventually another engineering team approached him because they recognized the quality of his work

**Result:** Faster onboarding, engineers are independent and productive, junior people are getting recognized across teams, people keep growing.

**Key Phrase:** "Build engineers who build products, not code yourself"

---

# 📄 PAGE 2: Reference Guides

## Self Introduction (60 seconds)

Hi, I'm Yong. I've spent over 20 years in software engineering across mobile development, backend systems, and engineering management. My leadership really focuses on three core things: building trust with people, giving engineers enough context so they can decide independently, and creating systems that help teams scale.

I'm excited about Google because the engineering culture—long-term thinking, emphasis on engineering excellence, and actually empowering engineers—aligns really well with how I like to work.

**Why leave current role?** 
I’m really grateful for my time at Grab. It gave me the opportunity to lead engineers, drive cross-team initiatives, and learn how to balance technical excellence with business delivery. Those experiences have shaped me a lot, and now I feel ready for the next challenge where I can continue learning and contribute at an even larger scale.

I'm looking for a place where I can have broader engineering impact, work with world-class engineers, and continue growing as a technical leader.

**Why Google?** 
First, I like the engineering culture. People challenge ideas with data instead of titles, and that’s how I like to work.

Second, I enjoy solving engineering problems at scale. The technical challenges are interesting, and good engineering decisions can create a lot of impact.

Third, I want to keep growing. I can learn from outstanding engineers, and as an Engineering Manager I can also help my team grow. I think that’s a good fit for what I enjoy doing.

I enjoy building maintainable systems and helping teams make better engineering decisions. I think Google has a very strong engineering culture, and that’s the environment where I believe I can contribute the most.
---

## Leadership Philosophy (Use when stuck)

- **Build Trust First** → People do their best work when there's transparent communication, respect, and psychological safety
- **Give People Context** → Don't just assign tasks. Help people understand the why and the business context so they can make good decisions
- **Grow Independent Engineers** → My job isn't solving problems myself. It's developing people who can solve problems independently
- **Engineering Excellence** → I encourage high standards through code reviews, architecture discussions, monitoring, and continuous improvement
- **Build Systems, Not Heroes** → Instead of solving the same problem repeatedly, I'd rather build scalable processes that help the entire organization

---

## Strengths & Weakness

### Key Strengths
- **Data-driven Decision Making** → I always gather evidence before making decisions
- **Building Scalable Systems** → I solve problems at scale instead of repeatedly
- **Coaching & Mentoring** → I help engineers grow and become independent
- **Long-term Thinking** → I balance short-term wins with long-term capability
- **Influence Without Authority** → I create value so people naturally want to collaborate

### Growth Area / Weakness

**Early Career:** I tended to communicate in way too much detail, especially when talking about technical topics. I'd overwhelm people with information instead of focusing on what actually mattered.

**How I've improved:** I learned to prioritize clarity over completeness. Now I lead with the key insight and let people ask for more details if they want them. I also realized that good communication really means understanding your audience and giving them what they need to know, not everything I know.

**Why this matters at Google:** At Google's scale, clear communication across teams is super critical. I've worked hard to be concise, focus on impact, and give people just the context they need to make decisions.

---

## Conflict Management (When asked about disagreement)

**My opening:** I don't see disagreement as a problem. Actually, in a team of strong engineers, respectful technical debate is healthy. My role is to create an environment where different perspectives can be discussed openly, and then help us converge on the best decision based on evidence.

**My framework:**
1. **Safe Environment** → I make it clear—we're reviewing ideas, not judging people
2. **Understand** → What are we actually disagreeing about? Goals? Priorities? Architecture?
3. **Align on the Goal** → What problem are we solving? What does success look like?
4. **Evaluate with Evidence** → Use data, experiments, customer impact—not just opinions
5. **Commit** → Everyone supports the decision fully
6. **Review** → If new evidence comes up, we're willing to adjust

---

## Red Flags to Avoid

❌ "The other team was slow" → ✅ "We misjudged our approach"
❌ "I built it alone" → ✅ "I worked with multiple teams"
❌ "We succeeded" (and stop) → ✅ "We delivered. Here's what I learned..."
❌ "We improved performance" → ✅ "We reduced latency from 500ms to 200ms"
❌ "I decided it was best" → ✅ "I chose this approach given constraint Y"
❌ "No trade-offs" → ✅ Always explain the trade-off

---
## Low performance issue:
I haven’t had a severe low-performance case requiring a formal PIP, but my approach would be three steps.

First, understand the root cause. I’d gather objective data and have a one-on-one conversation to understand whether the issue is skills, expectations, motivation, or something else.

Second, provide support. We’d agree on clear goals, create a practical improvement plan, meet regularly, and provide coaching or mentoring where needed.

Finally, make a fair decision based on evidence. If the engineer improves, that’s the best outcome. If not, after providing sufficient support, I’d work with HR and follow the company’s performance management process.

---

## Questions to Ask the Interviewer

**Pick 2-3 of these and ask at the end**

| Question | Why Ask | What to Listen For |
|---|---|---|
| **"What does success look like in your first year for this role?"** | Shows you think about outcomes | Do they have clarity? Is this actually a growth opportunity? |
| **"What's the biggest challenge facing your team right now?"** | Shows you want real problems, not the marketing version | Honesty. Specific challenges. Not complaints. |
| **"How does this team invest in growing engineers?"** | Shows your coaching philosophy matters | Concrete examples vs vague "we believe in growth" |
| **"What do you wish you'd known when you joined this team?"** | Shows you want honest talk and value their perspective | Honesty, humility, specific challenges |

**How to phrase it:** "I do have a few questions, if that's okay. First, what does success look like for this role in your first year? And then I'm curious—what's the biggest challenge facing your team right now?"

**DO:** Ask with genuine curiosity. Listen carefully. Take notes on their answers.

**DON'T:** Ask about salary, benefits, or hours. Don't ask loaded questions or interrupt them.

---

## Before You Walk In ✅

- [ ] Review 6 story openings (2 min)
- [ ] Remember the key phrases (1 min)
- [ ] Self-introduction flows naturally
- [ ] Pick 2-3 questions to ask at the end

**Remember:** Listen carefully to what competency they're asking about. Tell your 20-second opening → Pause for follow-ups → Use philosophy if you get stuck.

---

**You've got this. 🚀**
