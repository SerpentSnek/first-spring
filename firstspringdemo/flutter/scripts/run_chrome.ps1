# Launch the Flutter demo on Chrome
param (
  [switch]$SkipCreate
)

if (-not (Get-Command flutter -ErrorAction SilentlyContinue)) {
  Write-Error "Flutter CLI not found in PATH. Install Flutter and make sure 'flutter' is on PATH."
  exit 1
}

pushd (Split-Path -Path $MyInvocation.MyCommand.Definition -Parent)
cd ..

Write-Host "Running: flutter pub get"
flutter pub get

if (-not $SkipCreate) {
  Write-Host "Ensuring web platform files exist (flutter create .)"
  flutter create .
}

Write-Host "Enabling web support (flutter config --enable-web)"
flutter config --enable-web

Write-Host "Starting app on Chrome..."
flutter run -d chrome
popd