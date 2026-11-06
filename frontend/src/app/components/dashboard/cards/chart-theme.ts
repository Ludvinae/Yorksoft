// Colors for Chart.js charts, read from the active DaisyUI theme so text and gridlines follow it.
// Series colors come from a validated categorical palette (colorblind-safe for adjacent series),
// with a step for light themes and one for dark themes.

const SERIES_LIGHT = ['#2a78d6', '#eb6834'];
const SERIES_DARK = ['#3987e5', '#d95926'];

export type ChartTheme = {
  text: string;
  grid: string;
  surface: string;
  series: string[];
};

export function readChartTheme(): ChartTheme {
  const css = getComputedStyle(document.documentElement);
  const surface = css.getPropertyValue('--color-base-100').trim() || '#ffffff';

  return {
    text: css.getPropertyValue('--color-base-content').trim() || '#1f2937',
    grid: css.getPropertyValue('--color-base-300').trim() || '#e5e7eb',
    surface,
    series: isDark(surface) ? SERIES_DARK : SERIES_LIGHT,
  };
}

// DaisyUI 5 themes define colors as oklch(L C H), with L either 0-1 or a percentage
function isDark(color: string): boolean {
  const oklch = /oklch\(\s*([\d.]+)(%?)/i.exec(color);
  if (oklch) {
    const lightness = Number(oklch[1]) / (oklch[2] ? 100 : 1);
    return lightness < 0.5;
  }

  const hex = /^#([\da-f]{6})$/i.exec(color);
  if (hex) {
    const value = parseInt(hex[1], 16);
    const r = (value >> 16) & 255;
    const g = (value >> 8) & 255;
    const b = value & 255;
    return 0.2126 * r + 0.7152 * g + 0.0722 * b < 128;
  }

  return false;
}
