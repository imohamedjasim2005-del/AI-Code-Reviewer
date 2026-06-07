import { useState } from "react";

const ScoreCircle = ({ score, max = 100, label, color }) => {
  const pct = Math.min((score / max) * 100, 100);
  const r = 36, cx = 44, cy = 44;
  const circ = 2 * Math.PI * r;
  const offset = circ - (pct / 100) * circ;
  return (
    <div style={{ textAlign: "center" }}>
      <svg width="88" height="88">
        <circle cx={cx} cy={cy} r={r} fill="none" stroke="#1e293b" strokeWidth="8" />
        <circle cx={cx} cy={cy} r={r} fill="none" stroke={color} strokeWidth="8"
          strokeDasharray={circ} strokeDashoffset={offset}
          strokeLinecap="round" transform={`rotate(-90 ${cx} ${cy})`} />
        <text x={cx} y={cy + 5} textAnchor="middle" fontSize="15" fontWeight="bold" fill={color}>
          {score}
        </text>
      </svg>
      <div style={{ fontSize: "12px", color: "#64748b", marginTop: "4px", fontWeight: 600 }}>{label}</div>
    </div>
  );
};

const Badge = ({ text, color }) => (
  <span style={{
    background: color + "20", color, border: `1px solid ${color}50`,
    borderRadius: "999px", padding: "3px 12px", fontSize: "12px", fontWeight: 600
  }}>{text}</span>
);

const Section = ({ title, items, dotColor, textColor, bg, border }) => (
  items?.length > 0 ? (
    <div style={{ marginBottom: 20 }}>
      <h3 style={{ color: textColor, margin: "0 0 10px", fontSize: 14, fontWeight: 700 }}>{title}</h3>
      <div style={{ background: bg, borderRadius: 10, padding: 16, border: `1px solid ${border}` }}>
        {items.map((item, i) => (
          <div key={i} style={{ display: "flex", gap: 8, marginBottom: i < items.length - 1 ? 8 : 0 }}>
            <span style={{ color: dotColor, marginTop: 1 }}>•</span>
            <span style={{ color: textColor, fontSize: 13, lineHeight: 1.5 }}>{item}</span>
          </div>
        ))}
      </div>
    </div>
  ) : null
);

export default function App() {
  const [code, setCode] = useState("");
  const [fileName, setFileName] = useState("");
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [tab, setTab] = useState("paste"); // paste | upload

  const handleFileUpload = (e) => {
    const f = e.target.files[0];
    if (!f) return;
    setFileName(f.name);
    const reader = new FileReader();
    reader.onload = (ev) => setCode(ev.target.result);
    reader.readAsText(f);
    setResult(null);
    setError("");
  };

  const analyzeCode = async () => {
    if (!code.trim()) return;
    setLoading(true);
    setResult(null);
    setError("");

    try {
      const res = await fetch("http://localhost:8080/api/review", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          model: "claude-sonnet-4-20250514",
          max_tokens: 1000,
          system: `You are a code reviewer. Analyze the given code and respond ONLY with a valid JSON object, no markdown, no explanation, just raw JSON.
The JSON must have exactly these fields:
{
  "qualityScore": <integer 0-100>,
  "complexityScore": <integer, cyclomatic complexity estimate>,
  "language": "<detected language>",
  "issues": ["issue 1", "issue 2"],
  "suggestions": ["suggestion 1", "suggestion 2"],
  "strengths": ["strength 1"]
}`,
          messages: [{ role: "user", content: `Review this code:\n\n${code}` }]
        })
      });

      const data = await res.json();
      const text = data.content?.[0]?.text || "";
      const clean = text.replace(/```json|```/g, "").trim();
      const parsed = JSON.parse(clean);
      setResult(parsed);
    } catch (e) {
      setError("Analysis failed. Please try again.");
    }
    setLoading(false);
  };

  const reset = () => { setCode(""); setResult(null); setFileName(""); setError(""); };

  const qColor = (s) => s >= 75 ? "#22c55e" : s >= 50 ? "#f59e0b" : "#ef4444";
  const cColor = (s) => s <= 10 ? "#22c55e" : s <= 20 ? "#f59e0b" : "#ef4444";
  const qLabel = (s) => s >= 75 ? "Good Quality" : s >= 50 ? "Needs Improvement" : "Poor Quality";
  const cLabel = (s) => s <= 10 ? "Low Complexity" : s <= 20 ? "Medium Complexity" : "High Complexity";

  return (
    <div style={{ minHeight: "100vh", background: "linear-gradient(135deg,#0f172a 0%,#1e293b 100%)", fontFamily: "'Segoe UI',sans-serif", padding: "32px 16px" }}>
      <div style={{ maxWidth: 720, margin: "0 auto" }}>

        {/* Header */}
        <div style={{ textAlign: "center", marginBottom: 32 }}>
          <div style={{ fontSize: 42, marginBottom: 8 }}>🔍</div>
          <h1 style={{ color: "#f1f5f9", margin: 0, fontSize: 28, fontWeight: 700, letterSpacing: "-0.5px" }}>AI Code Reviewer</h1>
          <p style={{ color: "#64748b", margin: "8px 0 0", fontSize: 14 }}>Paste or upload your code for instant AI-powered analysis</p>
        </div>

        {/* Input Card */}
        {!result && (
          <div style={{ background: "#1e293b", borderRadius: 16, padding: 28, marginBottom: 24, border: "1px solid #334155" }}>

            {/* Tabs */}
            <div style={{ display: "flex", gap: 4, marginBottom: 20, background: "#0f172a", borderRadius: 10, padding: 4 }}>
              {["paste", "upload"].map(t => (
                <button key={t} onClick={() => setTab(t)} style={{
                  flex: 1, padding: "8px 0", borderRadius: 8, border: "none", cursor: "pointer", fontWeight: 600, fontSize: 13,
                  background: tab === t ? "linear-gradient(90deg,#3b82f6,#6366f1)" : "transparent",
                  color: tab === t ? "#fff" : "#64748b", transition: "all 0.2s"
                }}>
                  {t === "paste" ? "📝 Paste Code" : "📁 Upload File"}
                </button>
              ))}
            </div>

            {tab === "paste" ? (
              <textarea
                value={code}
                onChange={e => { setCode(e.target.value); setResult(null); setError(""); }}
                placeholder="// Paste your code here...&#10;public class Main {&#10;    public static void main(String[] args) {&#10;        System.out.println(&quot;Hello World&quot;);&#10;    }&#10;}"
                style={{
                  width: "100%", minHeight: 220, background: "#0f172a", color: "#e2e8f0",
                  border: "1px solid #334155", borderRadius: 10, padding: 16, fontSize: 13,
                  fontFamily: "'Courier New', monospace", resize: "vertical", outline: "none",
                  lineHeight: 1.6, boxSizing: "border-box"
                }}
              />
            ) : (
              <label style={{
                display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center",
                border: "2px dashed #475569", borderRadius: 12, padding: "40px 16px", cursor: "pointer",
                background: code ? "#0f172a" : "transparent", minHeight: 180
              }}>
                <input type="file" accept=".java,.py,.js,.ts,.cpp,.c,.cs,.go,.rb" onChange={handleFileUpload} style={{ display: "none" }} />
                {code && fileName ? (
                  <>
                    <span style={{ fontSize: 32 }}>📄</span>
                    <span style={{ color: "#38bdf8", fontWeight: 700, marginTop: 10 }}>{fileName}</span>
                    <span style={{ color: "#22c55e", fontSize: 12, marginTop: 6 }}>✅ File loaded — ready to analyze</span>
                  </>
                ) : (
                  <>
                    <span style={{ fontSize: 32 }}>☁️</span>
                    <span style={{ color: "#94a3b8", marginTop: 10, fontWeight: 600 }}>Click to upload a file</span>
                    <span style={{ color: "#475569", fontSize: 12, marginTop: 6 }}>.java .py .js .ts .cpp .c .cs .go</span>
                  </>
                )}
              </label>
            )}

            {error && (
              <div style={{ marginTop: 12, padding: "10px 14px", background: "#1a0505", borderRadius: 8, border: "1px solid #ef444440" }}>
                <span style={{ color: "#fca5a5", fontSize: 13 }}>⚠️ {error}</span>
              </div>
            )}

            <div style={{ display: "flex", gap: 10, marginTop: 16 }}>
              <button onClick={analyzeCode} disabled={!code.trim() || loading}
                style={{
                  flex: 1, padding: "13px 0", borderRadius: 10, border: "none", fontWeight: 700,
                  fontSize: 14, cursor: code.trim() && !loading ? "pointer" : "not-allowed",
                  background: code.trim() && !loading ? "linear-gradient(90deg,#6366f1,#8b5cf6)" : "#1e293b",
                  color: code.trim() && !loading ? "#fff" : "#475569", transition: "all 0.2s"
                }}>
                {loading ? "⏳ Analyzing your code..." : "🔍 Analyze Code"}
              </button>
              {code && (
                <button onClick={reset} style={{
                  padding: "13px 18px", borderRadius: 10, border: "1px solid #334155",
                  background: "transparent", color: "#94a3b8", fontWeight: 600, fontSize: 14, cursor: "pointer"
                }}>↺ Reset</button>
              )}
            </div>
          </div>
        )}

        {/* Loading */}
        {loading && (
          <div style={{ background: "#1e293b", borderRadius: 16, padding: 40, textAlign: "center", border: "1px solid #334155" }}>
            <div style={{ fontSize: 40, marginBottom: 12 }}>🤖</div>
            <p style={{ color: "#94a3b8", margin: 0 }}>Reviewing your code with AI...</p>
          </div>
        )}

        {/* Results */}
        {result && !loading && (
          <div style={{ animation: "fadeIn 0.4s ease" }}>

            {/* Scores */}
            <div style={{ background: "#1e293b", borderRadius: 16, padding: 28, marginBottom: 20, border: "1px solid #334155" }}>
              <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 24 }}>
                <h2 style={{ color: "#f1f5f9", margin: 0, fontSize: 16, fontWeight: 700 }}>📊 Analysis Report</h2>
                {result.language && <Badge text={result.language} color="#38bdf8" />}
              </div>

              <div style={{ display: "flex", justifyContent: "center", gap: 48, marginBottom: 20 }}>
                <ScoreCircle score={result.qualityScore} label="Quality Score" color={qColor(result.qualityScore)} />
                <ScoreCircle score={result.complexityScore} max={30} label="Complexity" color={cColor(result.complexityScore)} />
              </div>

              <div style={{ display: "flex", gap: 8, justifyContent: "center", flexWrap: "wrap" }}>
                <Badge text={qLabel(result.qualityScore)} color={qColor(result.qualityScore)} />
                <Badge text={cLabel(result.complexityScore)} color={cColor(result.complexityScore)} />
              </div>
            </div>

            {/* Details */}
            <div style={{ background: "#1e293b", borderRadius: 16, padding: 28, border: "1px solid #334155", marginBottom: 20 }}>
              <Section title="✅ Strengths" items={result.strengths}
                dotColor="#22c55e" textColor="#86efac" bg="#0f172a" border="#16532440" />
              <Section title="⚠️ Issues Found" items={result.issues}
                dotColor="#f87171" textColor="#fca5a5" bg="#0f172a" border="#7f1d1d40" />
              <Section title="💡 Suggestions" items={result.suggestions}
                dotColor="#fbbf24" textColor="#fde68a" bg="#0f172a" border="#78350f40" />
            </div>

            <button onClick={reset} style={{
              width: "100%", padding: "13px 0", borderRadius: 10, border: "1px solid #334155",
              background: "transparent", color: "#94a3b8", fontWeight: 600, fontSize: 14, cursor: "pointer"
            }}>↺ Review Another File</button>
          </div>
        )}

        <p style={{ textAlign: "center", color: "#334155", fontSize: 12, marginTop: 28 }}>
          React + Spring Boot · AI Code Reviewer · Built by Jas
        </p>
      </div>
      <style>{`@keyframes fadeIn { from { opacity:0; transform:translateY(12px) } to { opacity:1; transform:translateY(0) } }`}</style>
    </div>
  );
}