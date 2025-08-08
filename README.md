# NanoDiver - Powerful Prototype

This build contains:
- Native (NDK) scaffolding with JNI reading /proc/uptime (safe, read-only)
- Detailed system stats reader (meminfo, cpu temp, open sockets)
- Process listing from /proc
- Foreground service that logs to internal storage
- Coroutines-based Dashboard UI

Notes:
- Non-root devices are limited by Android sandboxing; this app reads only accessible files (/proc, /sys). It does NOT include instructions to bypass protections or gain root.
- For deeper capabilities (network sniffing, BPF), privileged access or native kernel modules are required.

Build:
- The included Gradle config expects a typical Android toolchain. Use GitHub Actions (workflow provided) or build locally with Android Studio + NDK.

