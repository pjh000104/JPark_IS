import CuisineList from "./CuisineList";
import { getCuisinesByRegion } from "@/app/lib/api";

type Props = {
  params: {
    region: string;
  };
};

export default async function RegionPage(props: Props) {
  const { region } = await props.params;
  const decodedRegion = decodeURIComponent(region);

  const cuisines = await getCuisinesByRegion(decodedRegion);

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <div className="bg-white border-b shadow-sm">
        <div className="max-w-5xl mx-auto px-6 py-8">
          <h1 className="text-3xl font-bold text-gray-800">
            {decodedRegion} Cuisines
          </h1>
          <p className="text-gray-500 mt-2">
            Click a cuisine to see details and reviews.
          </p>
        </div>
      </div>

      {/* Cuisines Grid */}
      <div className="max-w-5xl mx-auto px-6 py-10">
        <CuisineList cuisines={cuisines} region={decodedRegion} />
      </div>
    </div>
  );
}