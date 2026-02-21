"use client";

type Props = {
  cuisineName: string;
};

export default function GoogleMapsButton({ cuisineName }: Props) {
  const handleClick = () => {
    const url = `https://www.google.com/maps/search/${encodeURIComponent(
      cuisineName
    )}`;
    window.open(url, "_blank");
  };

  return (
    <button
      onClick={handleClick}
      className="mb-6 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
    >
      View on Google Maps
    </button>
  );
}