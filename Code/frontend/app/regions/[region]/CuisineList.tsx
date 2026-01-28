"use client";

type Props = {
  cuisines: string[];
  region: string;
};

export default function CuisineList({ cuisines, region }: Props) {
const openGoogleMaps = (cuisine: string) => {
  if (!navigator.geolocation) {
    alert("Geolocation is not supported by your browser");
    return;
  }

  navigator.geolocation.getCurrentPosition(
    (position) => {
      const { latitude, longitude } = position.coords;

      const url =
        `https://www.google.com/maps/search/?api=1` +
        `&query=${encodeURIComponent(cuisine)}` +
        `&center=${latitude},${longitude}` +
        `&radius=5000`;

      window.open(url, "_blank");
    },
    () => {
      // fallback
      const fallbackQuery = encodeURIComponent(`${cuisine} near me`);
      window.open(
        `https://www.google.com/maps/search/?api=1&query=${fallbackQuery}`,
        "_blank"
      );
    }
  );
};

  return (
    <ul>
      {cuisines.map((cuisine) => (
        <li
          key={cuisine}
          onClick={() => openGoogleMaps(cuisine)}
          className="cursor-pointer hover:underline"
        >
          {cuisine}
        </li>
      ))}
    </ul>
  );
}
