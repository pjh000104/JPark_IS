import {getCuisineByName, getReviewsByCuisineId,} from "@/app/lib/api";
import GoogleMapsButton from "./GoogleMapsButton";
import ReviewForm from "./ReviewForm";


export default async function CuisineDetailPage({ params }: any) {
  const { cuisineName } = await params;
  const decodedCuisineName = decodeURIComponent(
    cuisineName
  ).trim();

  const cuisine = await getCuisineByName(decodedCuisineName);

  if (!cuisine) {
    return <div>Cuisine not found</div>;
  }

  const reviews = await getReviewsByCuisineId(cuisine.id);

  return (
    <div className="min-h-screen bg-gray-100 py-10 px-6">
      <div className="max-w-3xl mx-auto bg-white shadow-lg rounded-lg p-8">
        <h1 className="text-3xl font-bold text-black mb-4">
          {cuisine.cuisineName}
        </h1>
        <p className="text-black mb-6">
          {cuisine.description}
        </p>

        <GoogleMapsButton cuisineName={cuisine.cuisineName} />

        {/* REVIEWS */}
        <h2 className="text-2xl font-semibold text-black mt-8 mb-4">
          Reviews
        </h2>

        {reviews.length === 0 ? (
          <p className="text-black">No reviews yet.</p>
        ) : (
          <div className="space-y-4 mb-8">
            {reviews.map((review: any) => (
              <div
                key={review.id}
                className="border border-gray-200 rounded p-4 bg-gray-50"
              >
                <p className="text-black font-semibold">
                  Rating: {review.rating}/5
                </p>
                <p className="text-black">{review.comment}</p>
              </div>
            ))}
          </div>
        )}

        {/* WRITE REVIEW (Client Component) */}
        <ReviewForm cuisineId={cuisine.id} />

      </div>
    </div>
  );
}